import { useState, useEffect } from "react";
import axios from "axios";
import EachCartItem from "./EachCartItem";
import CouponItem from "./CouponItem";
import { useCookies } from "react-cookie"

import { Button, TextField } from "@material-ui/core";
import styled from "styled-components";

const CartPage = ({ history }) => {
  const [cookies] = useCookies(["token"]);
  const {token} = cookies;
  const [cartList, setCartList] = useState(null);
  const [checkList, setCheckList] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [address, setAddress] = useState("");

  const [selectedCoupon, setSelectedCoupon] = useState({
    id: -1,
    discountRate: 0,
  });
  const [coupons, setCoupons] = useState([{
      id: -1,
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
    axios.get("api/cart/", { headers: { Authorization: token } })
      .then((res) => {
        if (res.data) {
          setCartList(res.data);
          res.data.map(({ id }) => checkList.push(id));
        }
      })
      .catch(() => console.log("토큰이 만료 되었습니다."));
  };

  const getCouponList = () => {
    axios.get("api/coupon", { headers: { Authorization: token }})
      .then((res) => setCoupons([...coupons, ...res.data]))
      .catch((err) => console.log("에러", err.response));
  };

  const deleteCartBook = () => {
    axios.delete(`api/cart/`, {
        data: [...checkList],
        headers: { Authorization: token },
      })
      .then(res => setCartList(res.data))
      .catch(err => console.log(err.response));
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

    axios.post("api/order", {
      orderList,
      address,
      coupon_id: selectedCoupon.id
    },{ headers: {Authorization: token }})
    .then(() => history.replace('/mypage/orderlist'))
    .catch(() => alert("상품은 하나라도 선택되어야 합니다. 혹은 주소를 입력하세요."))
  };

  return (
    <Container>
      {cartList && (
        <>
          {cartList.map((res, idx) => {
            return <EachCartItem data={res} cartList={cartList} setCartList={setCartList} checkEvent={checkEvent} key={idx} />;
          })}
          <ItemDeleteButton 
            variant="outlined" 
            color="secondary" 
            onClick={deleteCartBook}
            >
            장바구니에서 빼기
          </ItemDeleteButton>
          <AddressDiv>
            <AddressTitle>🏠 상품을 받을 도착지를 입력해주세요(필수)</AddressTitle>
            <AddressInput 
              placeholder="주소를 입력하세요" 
              required
              label="Your Address"
              variant="outlined"
              color="primary" 
              onChange={e => setAddress(e.target.value)}/ >
          </AddressDiv>
        </>
      )}
      <CouponTitle>🎟 적용할 쿠폰을 선택하여 주세요</CouponTitle>
      <CouponContainer>
        {coupons.length ? coupons.map((res, idx) => {
          return <CouponItem 
            data={res} 
            selectedCoupon={selectedCoupon} 
            setSelectedCoupon={setSelectedCoupon} 
            key={idx} 
            />;
        }) :
        null
        }
      </CouponContainer>

      <TotalTitle>💵 회원님의 장바구니에 총 가격</TotalTitle>
      <ResultContainer>
        <TotalContent textDecoration="line-through">
          {totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ￦
        </TotalContent>
        <TotalContent>→</TotalContent>

        <TotalContent fontSize="28px">
          ⭐️ {((totalPrice * (100 - selectedCoupon.discountRate)) / 100).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")} ￦ ⭐️
        </TotalContent>
      </ResultContainer>
      <div style={{display:"flex", justifyContent: "center"}}>
        <PayButton color="primary" variant="contained" onClick={payEvent}>
          결제하기
        </PayButton>
      </div>
    </Container>
  );
};
export default CartPage;

const Container = styled.div`
  margin: 0;
`;

const ResultContainer = styled.div`
  display: flex;
  height: auto;

  margin: 30px 10px;
`;
const CouponContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  margin: 30px;
  text-align: center;
`;
const CouponTitle = styled.div`
  margin-left: 30px;
  font-size: 20px;
  font-weight: 600;
`

const TotalTitle = styled.div`
  margin: 50px 0 3px 30px;

  font-size: 22px;
  font-weight: bolder;
  color: "#424242";
`;
const TotalContent = styled.div`
  margin: 0 60px;
  text-align: center;
  font-weight: 600;
  font-size: ${props => props.fontSize || "25px"};
  text-decoration: ${props => props.textDecoration || "none"};
`;
const ItemDeleteButton = styled(Button)`
  float: right;
  font-size: 16px;
`
const AddressDiv = styled.div`
  margin: 60px 30px;
`

const AddressInput = styled(TextField)`
  margin: 20px 0;
  width: 50vw;
  height: 40px;
`
const AddressTitle = styled.div`
  font-size: 20px;
  font-weight: 600;
`
const PayButton = styled(Button)`
  margin: 10px;
  font-size: 28px;
`