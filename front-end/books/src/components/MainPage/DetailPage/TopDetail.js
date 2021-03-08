import styled from "styled-components"
import CountBox from "./CountBox"

const TopDetail = (props) => {
  return <TopComponent>
    <Image />
    <Title>Books Title</Title>
    <Content top="80px">Author / Publisher</Content>
    <Content top="120px">Price</Content>
    <Content top="160px">Fee</Content>
    <Content top="200px">Count </Content>
    <CountBox />
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
  border:2px solid;
  background-color: white;
  margin: 0;

  position:relative;
  top:10px;
  left:270px;

  width:220px;
  height:280px;
`
const Title = styled.h2`
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