import styled from "styled-components";

const CouponItem = ({ data, setSelectedCoupon, selectedCoupon }) => {
  const { discountRate, name, id } = data;
  
  const selectCoupon = e => {
    setSelectedCoupon({
      ...selectCoupon,
      id, discountRate,
    })
  }

  return (
    <Container>
        <RadioButton name="coupon" type="radio" defaultChecked={id === null ? true : false} onClick={selectCoupon}/>
        <CouponName>{name}</CouponName>
        {discountRate !== 0 && <Discounts>{discountRate}%</Discounts>}
    </Container>
  );
};
export default CouponItem;

const Container = styled.div`
  display: inline-block;
  margin: 0 40px;
  vertical-align: center;
`;
const RadioButton = styled.input`
  margin: 10px 10px;
  
  width: 20px;
  height: 20px;

`;
const CouponName = styled.div``;
const Discounts = styled.span``;
