import { useState, useEffect } from "react"
import axios from "axios"

import { Button } from "react-bootstrap"
import styled from "styled-components"

const CartPage = ({ location }) => {
  const [cartList, setCartList] = useState(null);
  const [checked, setChecked] = useState({});
  const { state: { token } } = location;

  useEffect(() => {
    getCartBook()
  }, [])

  const getCartBook = props => {
    axios.get("http://localhost:8080/api/cart/", { headers: { Authorization: token } })
      .then(res => {
        const { data } = res
        if (Object.keys(data).length) {
          const { _embedded: { cartDtoList } } = data
          setCartList(cartDtoList)
          cartDtoList.map(itemId => {
            const cartNumber = itemId.itemDto.id
            setChecked({
              ...checked,
              [cartNumber]:cartNumber
            })
          })
        } else setCartList(null)
      })
      .catch(err => console.log("토큰이 만료 되었습니다."))
  }

  const deleteCartBook = cartId => {
    axios.delete(`http://localhost:8080/api/cart/${cartId}/`, { headers: { Authorization: token } })
      .then(res => {
        const { data } = res
        if (Object.keys(data).length) {
          const { _embedded: { cartDtoList } } = data
          setCartList(cartDtoList)

        } else setCartList(null)
      })
      .catch(err => console.log(err.response))
  }

  const clickEvent = (e, cartId) => {
    const isCheck = e.target.checked
    if (isCheck) {
      console.log(checked)
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
            console.log(res)
            const bookCount = res.quantity;
            const { id, name, imageUrl, price, quantity } = res.itemDto
            return <EachBookContainer key={idx}>
              <BookCheck type="checkbox" defaultChecked onClick={e => clickEvent(e, id)} />
              <BookImage src={imageUrl} alt={id} />
              <BookText>{name}</BookText>
              <BookText>{price}</BookText>
              <BookCount type="number" defaultValue={bookCount} name={id}
                onChange={() => { }} />
              <Button variant="danger" onClick={() => deleteCartBook(res.id)}>삭제</Button>
            </EachBookContainer>
          })}
        </div>
        <div>

        </div>
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
const BookCount = styled.input`

`