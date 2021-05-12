import {useState} from "react"
import axios from "axios"

import { Modal } from "react-bootstrap"
import { Button, Grid } from "@material-ui/core"
import styled from "styled-components"
const ENV = process.env.NODE_ENV;

const ModalPage = ({show, showOff, items}) => {
  
  return (
    <Modal
      onHide={showOff}
      show={show}
      size="lg"
      centered
      scrollable
      >
      <Modal.Header closeButton>
        <Modal.Title style={{color:"#448aff", fontSize:"30px", fontWeight:"800"}}>Ordered List</Modal.Title>
      </Modal.Header>
      <Modal.Body>
          <ContainerGrid container >
          {items.map((item, idx) => {
            const { orderedItem } = item
            return <ItemGrid container item alignItems="center" key={idx}>
              <Grid xs={6} item>
              { ENV === 'development' ? 
                <ModalImage src={orderedItem.imageUrl} alt={item.idx} />:
                <ModalImage rounded src={`/image/${orderedItem.uploadImageName}`} alt={item.idx}/>
              }
              </Grid>
              <Grid xs={6} item>
                <ModalContent 
                  fontSize="28px"
                  color="#616161"
                  >{orderedItem.name}</ModalContent>
                <ModalContent 
                  fontSize="16px"
                  color="#616161"
                  >{orderedItem.author}</ModalContent>
                <ModalContent 
                  fontSize="16px"
                  color="#616161"
                  >{orderedItem.publisher}</ModalContent>
                <ModalContent 
                  fontSize="16px"
                  colo="#616161"
                  >{orderedItem.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</ModalContent> 
              </Grid>
            </ItemGrid>
          })}
          </ContainerGrid>
      </Modal.Body>
      <Modal.Footer>
        <Button color="secondary" onClick={showOff}>Close</Button>
      </Modal.Footer>
    </Modal>
  );
}

const EachUserOrder = ({order, setOrders, token}) => {
	const [modalShow, setModalShow] = useState(false);
	const showOn = () => setModalShow(true);
	const showOff = () => setModalShow(false);

  const acceptOrder = () => {
    axios.patch(`api/admin/orders/shipping/${order.id}`, null, {headers: {Authorization: token}})
      .then(res => setOrders(res.data))
      .catch(err => console.log(err.response))
  };
	const cancelOrder = () => {
    axios.patch(`api/admin/orders/cancel/${order.id}`, null, {headers: {Authorization: token}})
      .then(res => setOrders(res.data))
      .catch(err => console.log(err.response))
  };

  const { createDate, paymentAmount, orderItemList, delivery: { status }} = order;
  return <> 
      <td>{createDate}</td>
      <td>{paymentAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ￦</td>
      <Td status={status}>{status}</Td>
      <ItemButton variant="outlined" fontcolor="#4caf50" onClick={showOn}>상세보기</ItemButton>
      <ItemButton variant="outlined" fontcolor="#3f51b5" disabled={status !== "READY"} onClick={acceptOrder}>주문수락</ItemButton>
      <ItemButton variant="outlined" fontcolor="#f57c00" disabled={status !== "READY"} onClick={cancelOrder}>주문취소</ItemButton>

      <ModalPage show={modalShow} showOff={showOff} items={orderItemList} />
    </>
}
export default EachUserOrder;

const ModalContent = styled.div`
  margin: 30px 0;
  font-size: ${props => props.fontSize };
  font-weight: bold;
  color: ${props => props.color};
  font-family: "Sunflower", sans-serif;
`

const ModalImage = styled.img`
	width: 300px;
	height: 350px;
	object-fit: cover;
`;
const ItemButton = styled(Button)`
  margin: 10px;

  background-color: ${props => props.fontcolor};
  color: white;

  &:hover{
    color: ${props => props.fontcolor};
    background-color: white;
  }
`
const Td = styled.td`
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
const ContainerGrid = styled(Grid)`
  
`
const ItemGrid = styled(Grid)`
  margin: 10px;
`