import TopDetail from "./TopDetail";
import MidDetail from "./MidDetail";
import ReviewList from "./ReviewList";

import styled from "styled-components";

const ItemDetail = ({ location }) => {
  const { book } = location.state
  
  return <DetailComponent>
    <TopDetail book={book}/>
    <Divider />
    <MidDetail description={book.description}/>
    <Divider />
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
  /* height: 0.4px; */
  background-color:#919191;
`