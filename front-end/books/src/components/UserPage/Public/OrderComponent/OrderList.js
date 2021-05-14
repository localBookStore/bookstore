import OrderPage from "./OrderPage";
import { useState, useEffect } from "react";
import { useCookies } from "react-cookie"
import axios from "axios";

import { Table, TableHead, TableContainer, TableRow, TableCell, Paper } from "@material-ui/core";
import styled from "styled-components";

const OrderList = () => {
	const [orders, setOrders] = useState(null);
  const [cookies] = useCookies(["token"])
  const token = cookies.token

	useEffect(() => {
		axios.get("api/mypage/order", { headers: { Authorization: token } })
      .then((res) => setOrders(res.data))
      .catch((err) => console.log(err.response));
	}, []);

  return <TableContainer component={Paper} style={{ margin:"0 30px"}} >
      <Table  style={{width:"70vw"}}>
        <TableHead>
          <TableRow >
            <StyledTableCell>주문일자</StyledTableCell>
            <StyledTableCell>배송지</StyledTableCell>
            <StyledTableCell>배송상태</StyledTableCell>
            <StyledTableCell>배송비</StyledTableCell>
            <StyledTableCell></StyledTableCell>
          </TableRow>
        </TableHead>
        {orders && orders.map((order, idx) => {
          return <OrderPage order={order} setOrders={setOrders} key={idx} token={token} />;
        })}
      </Table>
  </TableContainer>
};
export default OrderList;

const StyledTableCell = styled(TableCell)`
  font-size: 1.2vw;
  font-weight: bold;
  text-align: center;
`