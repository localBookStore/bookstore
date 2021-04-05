import { useState, useEffect } from "react";
import axios from "axios";
import EachUserOder from "./EachUserOrder"

import styled from "styled-components";

const UserOrder = ({ location }) => {
	const [orders, setOrders] = useState([]);
	const { user, token } = location.state;
	useEffect(() => {
		axios
			.get(`http://localhost:8080/api/admin/members/${user.id}/orders`, { headers: { Authorization: token } })
			.then((res) => setOrders(res.data))
			.catch((err) => console.log(err.response));
	}, []);

	return (
		<Container>
			<div>
				<h3>{user.nickName}</h3>님의 주문 목록입니다.
			</div>
      <OrderTag>
        <TagContent>수정 날짜</TagContent>
        <TagContent>총 가격</TagContent>
        <TagContent>현재 상태</TagContent>
        <div></div><div></div>
      </OrderTag>
			{orders &&
				orders.map((order, idx) => {
					return <EachUserOder order={order} setOrders={setOrders} token={token} key={idx} />;
				})}
		</Container>
	);
};
export default UserOrder;

const Container = styled.div`
  
`;
const OrderTag = styled.div`
  display: flex;
  justify-content: space-between;

`
const TagContent = styled.div`
  text-align: center;
  font-size: 22px;
  width: 90px;
  margin: 20px;
`
