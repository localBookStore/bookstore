import styled from "styled-components"
import CountBox from "./CountBox"

const TopDetail = ({props}) => {
  const {name, author, imageUrl, price, publisher, quantity, id} = props

  return <TopComponent>
    <Image src={imageUrl} alt={id}/>
    <Title>책제목: {name}</Title>
    <Content top="80px">저자: {author} / 출판사: {publisher}</Content>
    <Content top="120px">가격: {price}</Content>
    <Content top="160px">택배비: 3000</Content>
    <Content top="200px">남은 수량: {quantity}</Content>
    <CountBox rest={quantity} />
    <Button left="600px">장바구니</Button>
    <Button left="760px">바로구매</Button>
  </TopComponent>
}
export default TopDetail;

const TopComponent = styled.div`
  position:relative;
  width: 100%;
  height: auto;
`
const Image = styled.img`
  border:1px solid black;
  background-color: transparent;
  margin: 0;

  position:relative;
  top:10px;
  left:270px;

  width:220px;
  height:280px;
`
const Title = styled.h3`
  position: absolute;
  top: 20px;
  left: 600px;

  font-weight: 700;
`
const Content = styled.h5`
  position: absolute;
  left: ${props => props.left || "620px"};
  top: ${props => props.top};

  font-weight: bold;
`
const Button = styled.button`
  position: absolute;
  top:250px;
  left : ${props => props.left};

  width: 100px;
  height: 45px;
`