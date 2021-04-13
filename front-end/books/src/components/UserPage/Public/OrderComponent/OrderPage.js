import { useState } from "react"
import axios from "axios"

import { Button, TableBody, TableRow, TableCell } from "@material-ui/core"
import { Modal, ProgressBar } from "react-bootstrap"
import styled from "styled-components"

const DeliveryBar = ({status, percent}) => {
  if (status === "READY"){
    return <Section>
      상품 준비중
    </Section>
  } else if (status === "CANCEL"){
    return <Section>
      취소됨
    </Section>
  } else if (status === "SHIPPING" && percent < 100){
    return <Section>
      <ProgressBar animated variant="info" now={percent} label={`${percent}%`} />
    </Section>
  } else{
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
  
  const nowStatus = status !== "SHIPPING" ? status : progress < 100 ? "SHIPPING" : "COMPLETE" 

  return <TableBody>
    <TableRow>
      <StyledTableCell>{modifiedDate}</StyledTableCell>
      <StyledTableCell>{address}</StyledTableCell>
      <StyledTableCell status={nowStatus}>{nowStatus}</StyledTableCell>
      <StyledTableCell>{deliveryCharge.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</StyledTableCell>
      <StyledTableCell>
        <StyledButton color="primary" variant="outlined" onClick={showOn}>상세보기</StyledButton>
        <StyledButton color="secondary" variant="outlined" disabled={status === "CANCEL"} onClick={cancelOrder}>주문취소</StyledButton>
      </StyledTableCell>
    </TableRow>

    <ModalPage show={modalShow} showOff={showOff} items={orderItemList} status={status} progress={progress} />
  </TableBody>
}
export default OrderPage;


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
const StyledTableCell = styled(TableCell)`
  font-size: 18px;
  text-align: center;
  font-weight: 530;

  color: ${props => {
    if (props.status === "CANCEL"){
      return "#f73378"
    } else if (props.status === "SHIPPING") {
      return "#2979ff"
    } else if (props.status === "READY") {
      return "#ff9800"
    } else if (props.status === "COMPLETE") {
      return "#00a152"
    }
  }}
`
const StyledButton = styled(Button)`
  margin: 0 10px;;
`
