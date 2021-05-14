import { useState, useEffect } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import EachUserOder from "./EachUserOrder"

import { Table } from "react-bootstrap"
import styled from "styled-components";

const UserOrder = ({ userInfo }) => {
	const [orders, setOrders] = useState([]);
	const [cookies] = useCookies(["token"])
	const token = cookies.token;
	const {id, nickName} = userInfo;

	useEffect(() => {
		axios.get(`api/admin/members/${id}/orders`, { headers: { Authorization: token } })
			.then((res) => setOrders(res.data))
			.catch((err) => console.log(err.response));
	}, [id]);

	return (
		<Container>
			<div style={{marginBottom:"10px"}}>
				<h3 style={{display:"inline"}}>📝 {nickName}</h3> 님의 주문들입니다.
			</div>
			<Table responsive style={{width:"50vw"}}>
				<thead>
					<Tr>
						<Th>수정 날짜</Th>
						<Th>총 가격</Th>
						<Th>현재 상태</Th>
					</Tr>
				</thead>
				<tbody>
					{orders && orders.map((order, idx) => {
						return <Tr>
								<EachUserOder order={order} setOrders={setOrders} token={token} key={idx} />
							</Tr>
						})}
				</tbody>

			</Table>
		</Container>
	);
};
export default UserOrder;

const Container = styled.div`
  margin-left: 5vw;;
`;
const Tr = styled.tr`
	text-align: center;
	font-size: 1vw;
`
const Th = styled.th`
	font-size: 1vw;
`;