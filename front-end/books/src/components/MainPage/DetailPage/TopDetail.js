import { useCookies } from "react-cookie"
import { useHistory } from "react-router-dom"
import axios from "axios"

import { Image, OverlayTrigger, Tooltip } from "react-bootstrap"
import { Paper, Button } from "@material-ui/core"
import styled from "styled-components"
import { genreMap } from "feature/GenreMap"
const ENV = process.env.NODE_ENV;

const TopDetail = ({ book }) => {
  const { name, author, imageUrl, price, publisher, quantity, id, category_id, uploadImageName } = book
  const history = useHistory();
  const [cookies] = useCookies(['token']);

  const addCart = e => {
    const feature = e.target.name;

    axios.post(`api/cart/${id}/`, {
      orderCount: 1
    }, { headers: { Authorization: cookies.token }})
      .then(() => {
        if (feature === "directBuy") history.push({
          pathname: "/cart",
          state: { token: cookies.token}
        })
      })
      .catch(err => console.log(err.response))
    }

  return <>
  <Container>
      <Paper elevation={9} variant="outlined">
        {
          ENV === 'development' ?
          <PosterImage src={imageUrl} elevation={8} alt={id} rounded fluid /> :
          <PosterImage src={`/image/${uploadImageName}`} alt={id} rounded fluid />
        }
      </Paper>
      <ContentsContainer>
          <TagNames>
            {["이름", "저자", "출판사", "장르", "가격", "남은 수량"].map((tag, idx) => (
              <div key={idx}>{tag}</div>
            ))}
          </TagNames>

          <Contents>
            <Content>{name}</Content>
            <Content>{author}</Content>
            <Content>{publisher}</Content>
            <Content>{genreMap[category_id]}</Content>
            <Content>{price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ￦</Content>
            <Content>{quantity}</Content>
          </Contents>
      </ContentsContainer>
    </Container>
      
      <Buttons>
        <OverlayTrigger
          placement="bottom"
          delay={{ show: 250, hide: 400 }}
          overlay={props => <Tooltip {...props}>장바구니에 담고 이동합니다.</Tooltip>}>
        <CartButton variant="contained" color="primary" onClick={addCart} left="760px" name="directBuy">바로구매</CartButton>
        </OverlayTrigger>
        <OverlayTrigger
          placement="bottom"
          delay={{ show: 250, hide: 400 }}
          overlay={props => <Tooltip {...props}>장바구니에 담기기만 합니다.</Tooltip>}>
        <CartButton variant="contained" color="primary" left="600px" onClick={addCart} name="containItem">장바구니</CartButton>
        </OverlayTrigger>
      </Buttons>
  </>
}
export default TopDetail;

const Container = styled.div`
  
  display:flex;
  justify-content: center;
`
const ContentsContainer = styled.div`
  margin: 0 50px;
  display: flex;
  justify-content: space-between;
`
const TagNames = styled.div`
  display: flex;
  flex-direction: column;

  width: 80px;
  justify-content: space-between;
  font-size: 18px;
`
const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 400px;
`
const PosterImage = styled(Image)`
  width: 300px;
  height: auto;
  object-fit: cover;
`

const CartButton = styled(Button)`
  width: 10%;
  font-size: 20px;
  margin: 0 20px;
`
const Content = styled.div`
  font-size: 20px;
  font-weight: bold;
`
const Buttons = styled.div`
  position: relative;
  text-align: center;
  margin: 40px 0;
`