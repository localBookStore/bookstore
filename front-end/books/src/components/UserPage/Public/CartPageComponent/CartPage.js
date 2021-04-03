import { useState, useEffect } from "react";
import axios from "axios";
import EachCartItem from "../EachCartItem";

import { Button } from "react-bootstrap";
import styled from "styled-components";

const CartPage = ({ location }) => {
  const [cartList, setCartList] = useState(null);
  const [checkList, setCheckList] = useState(new Set());
  const [totalPrice, setTotalPrice] = useState(0);
  const {
    state: { token },
  } = location;

  useEffect(() => {
    getCartBook();
  }, []);

  const getCartBook = () => {
    axios
      .get("http://localhost:8080/api/cart/", {
        headers: { Authorization: token },
      })
      .then((res) => {
        const { data } = res;
        if (data) {
          setCartList(data);
          data.map(({ id, price }) => {
            checkList.add(id)
            setTotalPrice(prev => prev+price)
          });
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
      })
      .catch((err) => console.log(err.response));
  };

  const checkEvent = (id, isChecked, itemPrice) => {
    if (isChecked) {
      checkList.add(id);
      setCheckList(checkList);
      setTotalPrice(prev => prev + itemPrice)
    } else if (!isChecked && checkList.has(id)) {
      checkList.delete(id);
      setCheckList(checkList);
      setTotalPrice(prev => prev - itemPrice)
    }
  };
  return (
    <Container>
      {cartList === null ? (
        <div>장바구니가 비었습니다.</div>
      ) : (
        <>
          {cartList.map((data, idx) => {
            return <EachCartItem 
            data={data.item} 
            cartId={data.id} 
            orderCount={data.orderCount}
            checkEvent={checkEvent}
            totalPrice={totalPrice}
            setTotalPrice={setTotalPrice}
            key={idx} />;
          })}
          <Button variant="danger" onClick={deleteCartBook}>
            삭제
          </Button>

          <ResultContainer>
            <TotalPriceContainer>
              <TotalTitle>Total</TotalTitle>
              <TotalContent>{totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</TotalContent>
            </TotalPriceContainer>
          </ResultContainer>
        </>
      )}
    </Container>
  );
};
export default CartPage;

const Container = styled.div`
  /* width: 90%; */
  margin: 0;
`;

const ResultContainer = styled.div`
  display: flex;
  height: auto;

  margin: 30px 10px;
`;
const CouponContainer = styled.div`
  border: 2px solid black;
  margin: 30px 10px;
  width: 90%;
`;

const TotalPriceContainer = styled.div`
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
