import TopDetail from "./TopDetail";
import MidDetail from "./MidDetail";
import BottomDetail from "./BottomDetail";
import axiois from "axios"

import styled from "styled-components";

const ItemDetail = ({location}) => {
  const {state:{book}} = location
  console.log(book)

  return <DetailComponent>
    <TopDetail />
    <Divider />
    <MidDetail />
    <Divider margin-top="20px"/>
    <BottomDetail />
  </DetailComponent>
}
export default ItemDetail;


const DetailComponent = styled.div`
  position: relative;
  height: auto;
`

const Divider = styled.hr`
  border: solid none;
  height: 2px;
  background-color:#919191;
  margin-top: ${props => props["margin-top"] || "60px"};
`