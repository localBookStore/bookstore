import { useState, useEffect } from "react";
import axios from "axios";

import { Button } from "react-bootstrap";
import styled from "styled-components";

const CartPage = ({ location }) => {
  const [cartList, setCartList] = useState(null);
  const [checkList, setCheckList] = useState(new Set());
  const [isChange, setIsChange] = useState(false);
  const [totalPrice, setTotalPrice] = useState(0);
  const {
    state: { token },
  } = location;

  useEffect(() => {
    getCartBook();
  }, []);

  useEffect(() => {
    if (isChange) {
      setTimeout(() => {
        let total = 0;
        cartList.map((obj) => {
          const { quantity, price, id } = obj;
          if (checkList.has(id)) total += quantity * price;
        });
        setTotalPrice(total);

        return clearTimeout();
      }, 200);
      return setIsChange(false);
    }
  }, [isChange]);

  const minus = (idx) => {
    const newCartList = [...cartList];
    const nowValue = newCartList[idx].quantity;
    newCartList[idx].quantity = nowValue > 1 ? nowValue - 1 : 1;
    setCartList(newCartList);
    setIsChange(true);
  };
  const plus = (idx, maxValue) => {
    const newCartList = [...cartList];
    const nowValue = newCartList[idx].quantity;
    newCartList[idx].quantity = nowValue === maxValue ? nowValue : nowValue + 1;
    setCartList(newCartList);
    setIsChange(true);
  };
  const getCartBook = (props) => {
    axios
      .get("http://localhost:8080/api/cart/", {
        headers: { Authorization: token },
      })
      .then((res) => {
        const { data } = res;
        if (data) {
          data.map((bookInfo) => checkList.add(bookInfo.id));
          setCartList(data);
        }
      })
      .catch((err) => console.log("토큰이 만료 되었습니다."));
  };
  const deleteCartBook = () => {
    axios
      .delete(`http://localhost:8080/api/cart/`, {
        data: [...checkList],
        headers: { Authorization: token },
      })
      .then((res) => {
        setCartList(res.data);
        setCheckList(new Set());
        setIsChange(true);
      })
      .catch((err) => console.log(err.response));
  };

  const clickEvent = (cartId, isChecked) => {
    setIsChange(true);
    if (isChecked) {
      checkList.add(cartId);
      setCheckList(checkList);
    } else {
      checkList.delete(cartId);
      setCheckList(checkList);
    }
  };
  {
    console.log(cartList);
  }
  return (
    <Container>
      {cartList === null ? (
        <div>장바구니가 비었습니다.</div>
      ) : (
        <>
          <div>
            {cartList.map((res, idx) => {
              const cartId = res.id;
              const { id, name, imageUrl, price, orderCount } = res.itemDto;
              return (
                <EachBookContainer key={idx}>
                  <BookCheck
                    type="checkbox"
                    defaultChecked
                    onClick={(e) => clickEvent(cartId, e.target.checked)}
                  />
                  <BookImage src={imageUrl} alt={id} />
                  <BookText>{name}</BookText>
                  <div>
                    <Button onClick={() => minus(idx)}>-</Button>
                    {res.quantity}
                    <Button onClick={() => plus(idx, orderCount)}>+</Button>
                  </div>
                  <BookText>{price * res.quantity}</BookText>
                </EachBookContainer>
              );
            })}
            <Button variant="danger" onClick={deleteCartBook}>
              삭제
            </Button>
          </div>
          <ResultContainer>
            <CouponContainer></CouponContainer>

            <TotalPriceContainer>
              <TotalTitle>Total</TotalTitle>
              <TotalContent>
                {totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
              </TotalContent>
            </TotalPriceContainer>
          </ResultContainer>
        </>
      )}
    </Container>
  );
};
export default CartPage;

const Container = styled.div`
  width: 90%;
`;
const EachBookContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin: 20px;
`;
const BookImage = styled.img`
  max-width: 100px;
  overflow: hidden;
`;
const BookText = styled.span``;
const BookCheck = styled.input`
  position: relative;
  left: 6%;

  width: 24px;
  height: 24px;
`;
const BookCountInput = styled.input`
  width: 10%;
  height: 35px;
  margin: 0px 20px;

  text-align: center;
`;

const ResultContainer = styled.div`
  display: flex;
  height: 100vh;

  margin: 30px;
`;
const CouponContainer = styled.div`
  border: 2px solid black;
  margin: 30px;
  width: 90%;
`;

const TotalPriceContainer = styled.div`
  float: right;

  display: flex;
  justify-content: center;
  align-items: center;

  border: 2px solid black;
  width: 30%;

  margin: 30px;
`;
const TotalTitle = styled.div`
  margin: 20px;

  font-size: 50px;
  font-weight: bolder;
  text-align: center;
`;
const TotalContent = styled.div`
  margin: 20px;

  text-align: center;
  font-weight: 600;
  font-size: 200%;
`;
