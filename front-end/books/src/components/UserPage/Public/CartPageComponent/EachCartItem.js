import { Button } from "@material-ui/core";
import styled from "styled-components";
const ENV = process.env.NODE_ENV;

const EachCartItem = ({ data, cartList, setCartList, checkEvent }) => {
  const { orderCount, id } = data
  const {item: {imageUrl, name, price, quantity, upload_image_name }} = data
  
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
      {/* <PosterImage src={imageUrl} /> */}
      { 
        ENV === 'development' ? 
        <PosterImage src={imageUrl} />:
        <PosterImage src={`/image/${upload_image_name}`}/>
      }
      <ItemDiv fontSize="1.5vw"fontWeight="bold" width="200px">{name}</ItemDiv>
      <ItemDiv fontSize="1.5vw">{price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ₩</ItemDiv>
      <CountDiv>
        <CountButton variant="outlined" onClick={minus}>-</CountButton>
        <ItemCount>{orderCount}</ItemCount>
        <CountButton variant="outlined" onClick={plus}>+</CountButton>
      </CountDiv>
      
      <TotalPrice>{(price * orderCount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ₩</TotalPrice>
    </Container>
  );
};
export default EachCartItem;

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  /* margin:30px 50px; */
`;
const PosterImage = styled.img`
  width: 16vw;
  height: auto;
  object-fit: contain;
`;
const ItemDiv = styled.div`
  font-weight: ${props => props.fontWeight || "400"};
  font-size: ${(props) => props.fontSize};
  width: ${props => props.width || "100px"};
  text-align: center;
`;
const ItemCount = styled.span`
  /* font-size: 1.4vw; */
  font-weight: bold;
  text-align: center;
  width: 30px;
`;
const CountButton = styled(Button)`
  font-weight: bold;
  font-size: 1.2vw;
  padding: 4px 0;
`;
const CheckBox = styled.input`
  width: 2vw;
  height: 2vw;
  margin-right: 10px; 
`;
const CountDiv = styled.div`
  width: 200px;
  display: flex;
  justify-content: center;
`
const TotalPrice = styled.div`
  width: 120px;
  text-align: center;
  font-size: 1.6vw;
  font-weight: bold;
`