import TopDetail from "./TopDetail";
import MidDetail from "./MidDetail";
import ReviewList from "./ReviewList";

import styled from "styled-components";

const ItemDetail = ({ location }) => {
  const { book } = location.state
  
  return <DetailComponent>
    <TopDetail props={book}/>
    <Divider />
    <MidDetail props={book}/>
    <Divider marginTop="20px"/>
    <ReviewList book={book} />
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
  margin-top: ${props => props.marginTop || "60px"};
`