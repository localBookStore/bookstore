import { useState } from "react"
import axios from "axios"

import { Button, Modal, ProgressBar } from "react-bootstrap"
import styled from "styled-components"

const DeliveryBar = ({status, percent}) => {
  switch (status){
    case "READY":
      return <div>
        상품 준비중
      </div>
    case "CANCEL":
      return <div>
        취소됨
      </div>
    case "SHIPPING":
      return <div>
        <ProgressBar animated variant="info" now={percent} label={`${percent}%`} />
      </div>
    default:
      return <div>
        <ProgressBar animated now={100} label="100%" />
      </div>
  }
}

const ModalPage = ({show, showOff, items, status}) => {
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
          {console.log(items)}
          <DeliveryBar status={status} />
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

  const {deliveryCharge, delivery, orderItemList, id} = order
  const {address, modifiedDate, status } = delivery

  const cancelOrder = () => {
    axios.patch(`http://localhost:8080/api/mypage/order/${id}`,null ,{ headers:{ Authorization: token }})
      .then(res => setOrders(res.data))
      .catch(err => console.log(err.response))
  }

  // console.log(order)
  return <Container>
    <OrderContent>{modifiedDate}</OrderContent>
    <OrderContent>{address}</OrderContent>
    <OrderContent>{status}</OrderContent>
    <OrderContent>{deliveryCharge}</OrderContent>
    <Button onClick={showOn}>상세보기</Button>
    <Button variant="danger" disabled={delivery.status === "CANCEL"} onClick={cancelOrder}>주문취소</Button>

    <ModalPage show={modalShow} showOff={showOff} items={orderItemList} status={delivery.status} />
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