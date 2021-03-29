import { useState, useEffect } from "react"
import axios from "axios"

import { Button } from "react-bootstrap"
import styled from "styled-components"

const CartPage = ({ location }) => {
  const [cartList, setCartList] = useState(null);
  // const [bookCount, setBookCount] = useState({});
  const { state: { token } } = location;

  useEffect(() => {
    axios.get("http://localhost:8080/api/cart/", { headers: { Authorization: token } })
      .then(res => {
        const { data } = res
        if (Object.keys(data).length) {
          const { _embedded: { cartDtoList } } = data
          setCartList(cartDtoList)
        } else {
          setCartList(null)
        }
      })
      .catch(err => console.log("토큰이 만료 되었습니다."))
  }, [])

  const changeCount = (current, limit, e) => {
    current = e.target.value
  }
  const deleteCartBook = cartId => {
    axios.delete(`http://localhost:8080/api/cart/${cartId}/`, {
      headers: {
        Authorization: token
      }
    })
      .then(res => {
        const { data } = res
        if (Object.keys(data).length) {
          const { _embedded: { cartDtoList } } = data
          setCartList(cartDtoList)
        } else {
          setCartList(null)
        }
      })
      .catch(err => console.log(err.response))
  }

  return <Container>
    {cartList === null ?
      <div>
        장바구니가 비었습니다.
    </div> :
      cartList.map((res, idx) => {
        const bookCount = res.quantity;
        const { id, name, imageUrl, price, quantity } = res.itemDto
        return <EachBookContainer key={idx}>
          <BookCheck type="checkbox" defaultChecked/>
          <BookImage src={imageUrl} alt={id} />
          <BookText>{name}</BookText>
          <BookText>{price}</BookText>
          <BookCount type="number" defaultValue={bookCount} name={id}
            onChange={e => changeCount(bookCount, quantity, e)} />
          <Button variant="danger" onClick={() => deleteCartBook(res.id)}>삭제</Button>
        </EachBookContainer>
      })}
  </Container>
}
export default CartPage;

const Container = styled.div`
  width:90%;
`
const EachBookContainer = styled.div`
  display:flex;
  justify-content:space-between;
  align-items: center;

  margin:20px;
`
const BookImage = styled.img`
  max-width: 100px;
  overflow: hidden;
`
const BookText = styled.span`
`
const BookCheck = styled.input`
  position: relative;
  left: 6%;

  width: 24px;
  height: 24px;
`
const BookCount = styled.input`

`