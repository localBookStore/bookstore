import {useState} from "react"
import axios from "axios"

import {Button, Modal} from "react-bootstrap"
import styled from "styled-components"

const ModalPage = ({ show, showOff, items }) => {
	return <Modal onHide={showOff} show={show} size="lg" centered scrollable>
    <Modal.Header closeButton>
      <Modal.Title>주문 상세내용</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      {items.map((item, idx) => {
        const { orderedItem } = item;
        return <ModalContainer key={idx}>
            <ModalImage src={orderedItem.imageUrl} />
            <ModalContent>{orderedItem.name}</ModalContent>
            <ModalContent>{orderedItem.price}원</ModalContent>
            <ModalContent>{item.orderCount}</ModalContent>
          </ModalContainer>
      })}
    </Modal.Body>
    <Modal.Footer>
      <Button variant="secondary" onClick={showOff}>닫기</Button>
    </Modal.Footer>
  </Modal>
};

const EachUserOrder = ({order, setOrders, token}) => {
	const [modalShow, setModalShow] = useState(false);
	const showOn = () => setModalShow(true);
	const showOff = () => setModalShow(false);

  const acceptOrder = () => {
    axios.patch(`http://localhost:8080/api/admin/orders/shipping/${order.id}`, null, {headers: {Authorization: token}})
      .then(res => setOrders(res.data))
      .catch(err => console.log(err.response))
  };
	const cancelOrder = () => {
    axios.patch(`http://localhost:8080/api/admin/orders/cancel/${order.id}`, null, {headers: {Authorization: token}})
      .then(res => setOrders(res.data))
      .catch(err => console.log(err.response))
  };

  const { createDate, paymentAmount, orderItemList, delivery: { status }} = order;
  return (
    <OrderContainer>
      <OrderContent>{createDate}</OrderContent>
      <OrderContent>{paymentAmount}</OrderContent>
      <OrderContent>{status}</OrderContent>
      <Button onClick={showOn}>상세보기</Button>
      <Button variant="success" disabled={status !== "READY"} onClick={acceptOrder}>주문 수락</Button>
      <Button variant="danger" disabled={status === "CANCEL"} onClick={cancelOrder}>주문취소</Button>

      <ModalPage show={modalShow} showOff={showOff} items={orderItemList} />
    </OrderContainer>
  );
}
export default EachUserOrder;

const OrderContainer = styled.div`
	display: flex;
	justify-content: space-around;
  margin: 10px 0;
`;
const OrderContent = styled.div`
  width: 20%;
  text-align: center;
`;

const ModalContainer = styled.div`
	display: flex;
	justify-content: space-evenly;
	align-items: center;
`;
const ModalContent = styled.div`
	font-size: 22px;
	font-weight: 700;
`;
const ModalImage = styled.img`
	width: 200px;
	height: 350px;
	object-fit: cover;
`;
