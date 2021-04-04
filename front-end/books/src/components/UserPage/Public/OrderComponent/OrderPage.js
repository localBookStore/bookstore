
import styled from "styled-components"

const OrderPage = ({order}) => {
  const {createDate, deliveryCharge, delivery, orderItemList} = order
  console.log(order)
  return <Container>
    <div>{createDate}</div>
    <div>{delivery.address}</div>
  </Container>
}
export default OrderPage;

const Container = styled.div`
  display: flex;
  justify-content: space-evenly;
`