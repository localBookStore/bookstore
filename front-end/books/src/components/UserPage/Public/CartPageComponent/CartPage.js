import { useState, useEffect } from "react";
import axios from "axios";
import EachCartItem from "./EachCartItem";
import CouponItem from "./CouponItem";
import { useCookies } from "react-cookie"

import { Button } from "react-bootstrap";
import styled from "styled-components";

const CartPage = ({ location, history }) => {
  const [cookies] = useCookies(["token"]);
  const {token} = cookies;
  const [cartList, setCartList] = useState(null);
  const [checkList, setCheckList] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [address, setAddress] = useState("");
  const [selectedCoupon, setSelectedCoupon] = useState({
    id: null,
    discountRate: 0,
  });
  const [coupons, setCoupons] = useState([
    {
      id: null,
      name: "쿠폰 미적용",
      discountRate: 0,
    },
  ]);

  useEffect(() => {
    getCartBook();
    getCouponList();
  }, []);

  useEffect(() => {
    if (cartList) {
      let total = 0;
      setTimeout(() => {
        cartList.map(({ price, orderCount, id }) => checkList.includes(id) && (total += price * orderCount));
        setTotalPrice(total);
      }, 200);
    }
  }, [cartList, checkList]);

  const getCartBook = () => {
    axios
      .get("http://localhost:8080/api/cart/", { headers: { Authorization: token } })
      .then((res) => {
        if (res.data) {
          setCartList(res.data);
          res.data.map(({ id }) => checkList.push(id));
        }
      })
      .catch((err) => console.log("토큰이 만료 되었습니다."));
  };

  const getCouponList = () => {
    axios
      .get("http://localhost:8080/api/coupon", {
        headers: { Authorization: token },
      })
      .then((res) => setCoupons([...coupons, ...res.data]))
      .catch((err) => console.log("에러", err.response));
  };

  const deleteCartBook = () => {
    axios
      .delete(`http://localhost:8080/api/cart/`, {
        data: [...checkList],
        headers: { Authorization: token },
      })
      .then((res) => setCartList(res.data))
      .catch((err) => console.log(err.response));
  };

  const checkEvent = (id, isChecked) => {
    if (isChecked) {
      setCheckList([...checkList, id]);
    } else {
      setCheckList(checkList.filter((checkId) => checkId !== id));
    }
  };

  const payEvent = () => {
    const orderList = [];
    cartList.map(({ id, orderCount }) => checkList.includes(id) && orderList.push({ id, orderCount }));

    axios.post("http://localhost:8080/api/order", {
      orderList,
      address,
      coupon_id: selectedCoupon.id
    },{ headers: {Authorization: token }})
    .then(res => history.replace('/mypage/orderlist'))
    .catch(err => alert("상품은 하나라도 선택되어야 합니다. 혹은 주소를 입력하세요."))
  };

  return (
    <Container>
      {cartList && (
        <>
          {cartList.map((res, idx) => {
            return <EachCartItem data={res} cartList={cartList} setCartList={setCartList} checkEvent={checkEvent} key={idx} />;
          })}
          <AddressTitle>수령지</AddressTitle>
          <AddressInput placeholder="주소를 입력하세요" required onChange={e => setAddress(e.target.value)}/ >
          <ItemDeleteButton variant="danger" onClick={deleteCartBook}>삭제</ItemDeleteButton>
        </>
      )}
      <CouponContainer>
        {coupons.length && coupons.map((res, idx) => {
          return <CouponItem data={res} selectedCoupon={selectedCoupon} setSelectedCoupon={setSelectedCoupon} key={idx} />;
        })}
      </CouponContainer>
      <ResultContainer>
        <TotalPriceContainer>
          <TotalTitle>Total</TotalTitle>
          <TotalContent>
            총 가격:
            {totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
          </TotalContent>
          <TotalContent>
            할인된 가격:
            {((totalPrice * (100 - selectedCoupon.discountRate)) / 100).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
          </TotalContent>
        </TotalPriceContainer>
      </ResultContainer>
      <Button variant="success" onClick={payEvent}>
        결제하기
      </Button>
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
  display: flex;
  justify-content: right;
  align-items: center;

  margin: 30px 10px;
  width: 90%;

  font-size: 20px;
`;

const TotalPriceContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border: 2px solid black;
  width: 90%;

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
  width: 800px;

  text-align: center;
  font-weight: 600;
  font-size: 200%;
`;
const ItemDeleteButton = styled(Button)`
  display: block
`
const AddressInput = styled.input`
  margin: 20px 0;
  width: 40%;
  height: 40px;
`
const AddressTitle = styled.div`
  font-size: 20px;
  font-weight: 600;
`