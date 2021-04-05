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

  console.log(orders)
  return <Container>
    <div>{user.nickName}</div>
  </Container>
}
export default UserOrder;

const Container = styled.div`

`