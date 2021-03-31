import { useState, useEffect } from "react"
import axios from "axios"

import { Button } from "react-bootstrap"
import styled from "styled-components"

const CartPage = ({ location }) => {
  console.log(location)
  const [cartList, setCartList] = useState(null);
  const [checkList, setCheckList] = useState(new Set());
  const { state: { token } } = location;

  useEffect(() => {
    getCartBook()
  }, [])

  const getCartBook = props => {
    axios.get("http://localhost:8080/api/cart/", { headers: { Authorization: token } })
      .then(res => {
        const { data } = res;
        setCartList(data)
        if (data) data.map(bookInfo => checkList.add(bookInfo.id))
      })
      .catch(err => console.log("토큰이 만료 되었습니다."))
  }

  const deleteCartBook = () => {
    axios.delete(`http://localhost:8080/api/cart/`, {
      data: [
        ...checkList
      ],
      headers: { Authorization: token }
    })
      .then(res => setCartList(res.data))
      .catch(err => console.log(err.response))
  }

  const clickEvent = (cartId, isChecked) => {
    if (isChecked) {
      checkList.add(cartId)
      setCheckList(checkList)
    } else {
      checkList.delete(cartId)
      setCheckList(checkList)
    }
  }

  return <Container>
    {cartList === null ?
      <div>
        장바구니가 비었습니다.
    </div> :
      <>
        <div>
          {cartList.map((res, idx) => {
            const cartId = res.id
            const { id, name, imageUrl, price, quantity } = res.itemDto
            return <EachBookContainer key={idx}>
              <BookCheck type="checkbox" defaultChecked onClick={e => clickEvent(cartId, e.target.checked)} />
              <BookImage src={imageUrl} alt={id} />
              <BookText>{name}</BookText>
              <BookText>{price}</BookText>
              <BookCountInput type="number"
                defaultValue={res.quantity}
                name="inputCount"
                required />
            </EachBookContainer>
          })}
        </div>
        <Button variant="danger" onClick={deleteCartBook}>삭제</Button>
      </>
    }
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
const BookCountInput = styled.input`
  width: 10%;
  height: 35px;
  margin: 0px 20px;

  text-align: center;
`