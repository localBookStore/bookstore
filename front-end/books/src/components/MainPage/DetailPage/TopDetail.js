import { useCookies } from "react-cookie"
import { useHistory } from "react-router-dom"
import axios from "axios"

import { Image, OverlayTrigger, Tooltip } from "react-bootstrap"
import { Paper, Button } from "@material-ui/core"
import styled from "styled-components"
import { genreMap } from "feature/GenreMap"
const ENV = process.env.NODE_ENV;

const TopDetail = ({ book }) => {
  const { name, author, imageUrl, price, publisher, quantity, id, category_id, upload_image_name } = book
  const history = useHistory();
  const [cookies] = useCookies(['token']);
  const token = cookies.token;

  const addCart = flag => {
    axios.post(`api/cart/${id}/`, {
      orderCount: 1
    }, { headers: { Authorization: token }})
      .then(() => {
        if (!!flag){
          history.push({
          pathname: "/cart",
          state: { token: cookies.token}
        })}
      })
      .catch(err => alert(err.response.data))
    }

  return <>
  <Container>
      <Paper elevation={9} variant="outlined">
        {
          ENV === 'development' ?
          <PosterImage src={imageUrl} elevation={8} alt={id} rounded fluid /> :
          <PosterImage src={`/image/${upload_image_name}`} alt={id} rounded fluid />
        }
      </Paper>
      <ContentsContainer>
          <TagNames>
            {["이름", "저자", "출판사", "장르", "가격", "남은 수량"].map((tag, idx) => (
              <TagName key={idx}>{tag}</TagName>
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
        <CartButton variant="contained" color="primary" onClick={() => addCart(true)} left="760px">바로구매</CartButton>
        </OverlayTrigger>
        <OverlayTrigger
          placement="bottom"
          delay={{ show: 250, hide: 400 }}
          overlay={props => <Tooltip {...props}>장바구니에 담기기만 합니다.</Tooltip>}>
        <CartButton variant="contained" color="primary" left="600px" onClick={() => addCart(false)}>장바구니</CartButton>
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
  width: 6vw;
  justify-content: space-between;
  font-size: 1.5vw;
`
const TagName = styled.div`
  font-size: 1.5vw;
`;

const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 40vw;
`
const PosterImage = styled(Image)`
  width: 25vw;
  object-fit: cover;
  box-shadow: 2px 2px 2px gray;
`

const CartButton = styled(Button)`
  width: 10vw;
  font-size: 1vw;
  margin: 0 20px;
  padding: 7px 0;
`
const Content = styled.div`
  font-size: 1.5vw;
  font-weight: bold;
  
`
const Buttons = styled.div`
  position: relative;
  text-align: center;
  margin: 40px 0;
`