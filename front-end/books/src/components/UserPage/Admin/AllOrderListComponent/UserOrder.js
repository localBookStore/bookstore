import { useState, useEffect } from "react"
import axios from "axios"

import styled from "styled-components"

const UserOrder = ({location}) => {
  const [orders, setOrders] = useState([]);
  const {user, token} = location.state
  
  useEffect(() => {
    axios.get(`http://localhost:8080/api/admin/members/${user.id}/orders`, { headers: { Authorization: token}})
      .then(res => setOrders(res.data))
      .catch(err => console.log(err.response))
  }, [])

  return <Container>
    <div><h3>{user.nickName}</h3>님의 주문 목록입니다.</div>
    {orders && orders.map((order, idx) => {
      return <OrderContainer key={idx}>
        {console.log(order)}
      </OrderContainer>
    })}
  </Container>
}
export default UserOrder;

const Container = styled.div`

`
const OrderContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
`