import { useState } from "react"
import axios from "axios"

import { Button, Modal, ProgressBar } from "react-bootstrap"
import styled from "styled-components"

const DeliveryBar = ({status, percent}) => {
  switch (status) {
    case "READY":
      return <Section>
        상품 준비중
      </Section>
    case "CANCEL":
      return <Section>
        취소됨
      </Section>
    case "SHIPPING":
      return <Section>
        <ProgressBar animated variant="info" now={percent} label={`${percent}%`} />
      </Section>
    default:
      return <Section>
        <ProgressBar animated now={100} label="100%" />
      </Section>
  }
}

const ModalPage = ({show, showOff, items, status, progress}) => {
  return (
    <Modal
      onHide={showOff}
      show={show}
      size="lg"
      centered
      scrollable
    >
      <Modal.Header closeButton>
        <Modal.Title>주문 상세내용</Modal.Title>
      </Modal.Header>
      <Modal.Body>
          <DeliveryBar status={status} percent={progress} />
          {items.map((item, idx) => {
            const { orderedItem } = item
        
            return <ModalContainer key={idx}>
              <ModalImage src={orderedItem.imageUrl} />
              <ModalContent>{orderedItem.name}</ModalContent>
              <ModalContent>{orderedItem.price}원</ModalContent>
              <ModalContent>{}</ModalContent>
            </ModalContainer>
          })}

      </Modal.Body>
      <Modal.Footer>
        <Button onClick={showOff}>Close</Button>
      </Modal.Footer>
    </Modal>
  );
}


const OrderPage = ({order, setOrders, token}) => {
  
  const [modalShow, setModalShow] = useState(false);
  const showOn = () => setModalShow(true);
  const showOff = () => setModalShow(false);

  
  const cancelOrder = () => {
    axios.patch(`http://localhost:8080/api/mypage/order/${id}`,null ,{ headers:{ Authorization: token }})
    .then(res => setOrders(res.data))
    .catch(err => console.log(err.response))
  }

  const {deliveryCharge, delivery, orderItemList, id} = order
  const {address, modifiedDate, status, progress } = delivery
  
  return <Container>
    <OrderContent>{modifiedDate}</OrderContent>
    <OrderContent>{address}</OrderContent>
    <OrderContent>{status}</OrderContent>
    <OrderContent>{deliveryCharge}</OrderContent>
    <Button onClick={showOn}>상세보기</Button>
    <Button variant="danger" disabled={status === "CANCEL"} onClick={cancelOrder}>주문취소</Button>

    <ModalPage show={modalShow} showOff={showOff} items={orderItemList} status={status} progress={progress} />
  </Container>
}
export default OrderPage;

const Container = styled.div`
  display: flex;
  justify-content: space-evenly;
  margin: 20px 0;
`
const OrderContent = styled.div`
  width: 20%;
`

const ModalContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
  align-items: center;
`
const ModalContent = styled.div`

`
const ModalImage = styled.img`
  width: 200px;
  height: 350px;
  object-fit: cover;
`
const Section = styled.div`
  margin: 20px;
`