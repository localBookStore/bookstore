import styled from "styled-components"

const ItemDetail = (props) => {
  return <DetailComponent>
    <Image>책이미지</Image>
    <Title>Books Title</Title>
  </DetailComponent>
}
export default ItemDetail;

const DetailComponent = styled.div`
  position:relative;
  width: 100%;
  height: auto;
`
const Image = styled.div`
  border:2px solid;
  background-color: white;

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