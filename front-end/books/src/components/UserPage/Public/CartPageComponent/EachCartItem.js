import { Button } from "react-bootstrap";
import styled from "styled-components";

const EachCartItem = ({ data, cartList, setCartList, checkEvent }) => {
  const { orderCount, id } = data
  const {item: {imageUrl, name, price, quantity }} = data
  
  const changeCheckEvent = (e) => {
    const { target: { checked } } = e;
    checkEvent(data.id, checked)
  };

  const minus = () => {
    setCartList(cartList.map(cart => cart.id === id ? {
      ...cart,
      orderCount: orderCount ? orderCount-1 : orderCount
    } : cart))
  }

  const plus = () => {
    setCartList(cartList.map(cart => cart.id === id ? {
      ...cart,
      orderCount: orderCount <  quantity ? orderCount+1 : orderCount
    } : cart))
  }

  return (
    <Container>
      <CheckBox type="checkbox" defaultChecked onChange={changeCheckEvent} />
      <PosterImage src={imageUrl} />
      <ItemDiv fontSize="28px" width="300px">{name}</ItemDiv>
      <ItemDiv fontSize="28px">{price}</ItemDiv>
      <div>
        <CountButton onClick={minus}>-</CountButton>
        <ItemCount>{orderCount}</ItemCount>
        <CountButton onClick={plus}>+</CountButton>
      </div>
      <ItemDiv>{price * orderCount}</ItemDiv>
    </Container>
  );
};
export default EachCartItem;

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin :10 px;
`;
const PosterImage = styled.img`
  width: 220px;
  height: 300px;
  object-fit: cover;
`;
const ItemDiv = styled.div`
  font-weight: 800;
  font-size: ${(props) => props.fontSize};
  width: ${props => props.width || "30px"};
  text-align: center;
`;
const ItemCount = styled.span`
  font-size: 20px;
  font-weight: bold;

  margin: 0 20px;
`;
const CountButton = styled(Button)``;
const CheckBox = styled.input`
  width: 30px;
  height: 30px;
`;
