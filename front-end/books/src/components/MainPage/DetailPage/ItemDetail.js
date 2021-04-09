import TopDetail from "./TopDetail";
import MidDetail from "./MidDetail";
import ReviewList from "./ReviewList";
import { useCookies } from "react-cookie"

import styled from "styled-components";

const ItemDetail = ({ location }) => {
  const { book } = location.state
  const [ cookies ] = useCookies(["token"])
  const { token } = cookies;
  
  console.log(book)
  return <DetailComponent>
    <TopDetail props={book}/>
    <Divider />
    <MidDetail props={book}/>
    <Divider marginTop="20px"/>
    <ReviewList book={book} token={token} />
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