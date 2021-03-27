import { useState, useEffect } from "react"
import axios from "axios"
import styled from "styled-components"

const CartPage = ({ location }) => {
  const [cartList, setCartList] = useState(null);
  const { state: { token } } = location;

  useEffect(() => {
    axios.get("http://localhost:8080/api/cart/", {headers:{Authorization:token}})
      .then(res => setCartList(res.data))
      .catch(err => console.log("토큰이 만료 되었습니다."))
  }, [])

  return <Container>


  </Container>
}
export default CartPage;

const Container = styled.div`

`