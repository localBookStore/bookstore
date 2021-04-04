import { useEffect, useState } from "react";

import { Button } from "react-bootstrap";
import styled from "styled-components";

const EachCartItem = ({ data, cartId, orderCount, checkEvent, setTotalPrice }) => {
  const [itemCount, setItemCount] = useState(orderCount);
  const [isChecked, setIsCheck] = useState(true);
  const { imageUrl, name, price, quantity } = data;

  // useEffect(() => {
  //   setTimeout(() => {
  //     if (!isChecked) {
  //       setTotalPrice(totalPrice - (itemCount*price))
  //     } else {
  //       setTotalPrice(totalPrice + (itemCount*price))
  //     }
  //   })
  // }, [itemCount, isChecked])

  const changeCheckEvent = (e) => {
    const { target: { checked } } = e;

    setIsCheck(!isChecked);
    checkEvent(cartId, checked, price*itemCount);
  };

  const minus = () => {
    if (itemCount) {
      setTotalPrice(prev => prev - price)
      setItemCount((prev) => prev - 1)
    };
    }
  const plus = () => {
    if (itemCount < quantity) {
      setTotalPrice(prev => prev + price)
      setItemCount(prev => prev + 1);
    }
  }

  return (
    <Container>
      <CheckBox type="checkbox" defaultChecked={isChecked} onChange={changeCheckEvent} />
      <PosterImage src={imageUrl} />
      <ItemDiv fontSize="28px" width="300px">{name}</ItemDiv>
      <ItemDiv fontSize="28px">{price}</ItemDiv>
      <div>
        <CountButton onClick={minus}>-</CountButton>
        <ItemCount>{itemCount}</ItemCount>
        <CountButton onClick={plus}>+</CountButton>
      </div>
      <ItemDiv>{price * itemCount}</ItemDiv>
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
