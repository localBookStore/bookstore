import OrderPage from "./OrderPage";
import { useState, useEffect } from "react";
import axios from "axios";

import { Button } from "react-bootstrap";
import styled from "styled-components";

const OrderList = ({ location }) => {
	const [orders, setOrders] = useState(null);
	const token = location.state.token;
	useEffect(() => {
		axios
			.get("http://localhost:8080/api/mypage/order", { headers: { Authorization: token } })
			.then((res) => setOrders(res.data))
			.catch((err) => console.log(err.response));
	}, []);

  return (
		<Container>
			<h3>주문 목록들 입니다.</h3>
      <ItemTag>
        <div>주문일자</div>
        <div>도착지</div>
        <div>배송상태</div>
        <div>배송비</div>
        <div></div>
        <div></div>
      </ItemTag>
			{orders && orders.map((order, idx) => {
					return <OrderPage order={order} setOrders={setOrders} key={idx} token={token} />;
				})}
		</Container>
	);
};
export default OrderList;

const Container = styled.div`

`;
const ItemTag = styled.h4`
	display: flex;
  justify-content: space-between;
  margin: 20px 0;
`;
