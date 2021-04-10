import { useCookies } from "react-cookie"
import { useHistory } from "react-router-dom"
import axios from "axios"

import { Image, Button, OverlayTrigger, Tooltip } from "react-bootstrap"
import styled from "styled-components"
import { genreMap } from "feature/GenreMap"

const TopDetail = ({ book }) => {
  const { name, author, imageUrl, price, publisher, quantity, id, category_id } = book
  const history = useHistory();
  const [cookies] = useCookies(['token']);

  const addCart = e => {
    const feature = e.target.name;

    axios.post(`http://localhost:8080/api/cart/${id}/`, {
      orderCount: 1
    }, { headers: { Authorization: cookies.token}})
      .then(() => {
        if (feature === "directBuy") history.push({
          pathname: "/cart",
          state: { token: cookies.token}
        })
      })
      .catch(err => console.log(err.response))
    }

  return <Container>
    <HorizonDiv>
      <PosterImage src={imageUrl} alt={id} rounded fluid />
      <TagNames>
        {["이름", "저자", "출판사", "장르", "가격", "남은 수량"].map((tag, idx) => (
          <div key={idx}>{tag}</div>
        ))}
        <OverlayTrigger
          placement="bottom"
          delay={{ show: 250, hide: 400 }}
          overlay={props => <Tooltip {...props}>장바구니에 담기기만 합니다.</Tooltip>}>
        <CartButton variant="outline-success" left="600px" onClick={addCart} name="containItem">장바구니</CartButton>
        </OverlayTrigger>
      </TagNames>
      <Contents>
        <Content>{name}</Content>
        <Content>{author}</Content>
        <Content>{publisher}</Content>
        <Content>{genreMap[category_id]}</Content>
        <Content>{price}</Content>
        <Content>{quantity}</Content>
        <OverlayTrigger
          placement="bottom"
          delay={{ show: 250, hide: 400 }}
          overlay={props => <Tooltip {...props}>장바구니에 담고 이동합니다.</Tooltip>}>
        <CartButton variant="outline-success" onClick={addCart} left="760px" name="directBuy">바로구매</CartButton>
        </OverlayTrigger>
      </Contents>
    </HorizonDiv>
  </Container>
}
export default TopDetail;

const Container = styled.div`
`
const HorizonDiv = styled.div`
  display:flex;
  justify-content: center;
`
const TagNames = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;
  margin-left: 5%;
`
const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
`
const PosterImage = styled(Image)`
  width: 300px;
  height: auto;
  object-fit: cover;
`

const CartButton = styled(Button)`
  width: 100px;
  height: 45px;
`
const Content = styled.div`
  font-size: 20px;

  font-weight: bold;
`