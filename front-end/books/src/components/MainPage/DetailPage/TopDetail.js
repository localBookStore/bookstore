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
        })} else{
          alert("장바구니에 담겼습니다.")
        }
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
            <Div>
              <Tag>이름</Tag>
              <Content>{name}</Content>
            </Div>
            <Div>
              <Tag>저자</Tag>
              <Content>{author}</Content>
            </Div>
            <Div>
              <Tag>출판사</Tag>
              <Content>{publisher}</Content>
            </Div>
            <Div>
              <Tag>장르</Tag>
              <Content>{genreMap[category_id]}</Content>
            </Div>
            <Div>
              <Tag>가격</Tag>
              <Content>{price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ￦</Content>
            </Div>
            <Div>
              <Tag>남은 수량</Tag>
              <Content>{quantity}</Content>
            </Div>

      </ContentsContainer>
    </Container>
      { token === undefined ? 
      <NeedLoginDiv>😓 선택한 도서를 구매하시려면 로그인을 해주세요</NeedLoginDiv>
      :
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
      }
  </>
}
export default TopDetail;

const Container = styled.div`
  display:flex;
  justify-content: center;
`
const ContentsContainer = styled.div`
  margin-left: 50px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`
const Div = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
 
`
const Tag = styled.div`
  font-size: 1vw;
`

const PosterImage = styled(Image)`
  width: 25vw;
  object-fit: contain;
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
  margin: 0 auto;
  padding: 10px;

`
const Buttons = styled.div`
  position: relative;
  text-align: center;
  margin: 40px 0;
`
const NeedLoginDiv = styled.div`
  margin: 3vw 0;
  text-align: center;
  font-size: 3vw;
  font-weight: bold;
`