import { useState } from "react"
import axios from "axios"

import { Button, TableBody, TableRow, Paper, TableCell, Grid } from "@material-ui/core"
import { Modal, ProgressBar } from "react-bootstrap"
import styled from "styled-components"

const DeliveryBar = ({status, percent}) => {
  if (status === "READY"){
    return <Section>
      상품 준비중...
    </Section>
  } else if (status === "CANCEL"){
    return <Section>
      상품이 취소되었습니다.
    </Section>
  } else if (status === "SHIPPING" && percent < 100){
    return <Section>
      <StyledProgressBar animated variant="info" now={percent} label={`${percent}%`} />
    </Section>
  } else{
    return <Section>
      <StyledProgressBar animated now={100} label="100%" />
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
          <Grid container >
          {items.map((item, idx) => {
            const { orderedItem } = item
            return <Grid container item spacing={2} key={idx}>
              <Grid xs={6} item>
                <ModalImage src={orderedItem.imageUrl} alt={item.title}/>
              </Grid>
              <Grid xs={6} item>
                <ModalContent>{orderedItem.name}</ModalContent>
                <ModalContent>{orderedItem.price}원</ModalContent> 
              </Grid>
            </Grid>
          })}
          </Grid>
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

const ModalContent = styled.div`
  font-size: 24px;
  text-align: center;
`

const ModalImage = styled.img`
  width: 300px;
`
const Section = styled.div`
  margin: 20px;
  vertical-align: center;
  font-size: 22px;
  font-family: "Sunflower", sans-serif;
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

const StyledProgressBar = styled(ProgressBar)`
  height: 30px;
`

const ItemGrid = styled(Grid)`
`
const ContainerGrid = styled(Grid)`
  
`