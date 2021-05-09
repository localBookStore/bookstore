import { Radio } from "@material-ui/core"
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
        <RadioButton name="coupon" type="radio" checked={selectedCoupon.id === id ? true : false} onClick={selectCoupon}/>
        <CouponName>{name}</CouponName>
        {discountRate !== 0 && <Discounts>{discountRate} %</Discounts>}
    </Container>
  );
};
export default CouponItem;

const Container = styled.div`
  
  
`;
const RadioButton = styled(Radio)`
  margin: 10px 10px;
  color: red;
  width: 20px;
  height: 20px;

`;
const CouponName = styled.div`
  font-size: 22px;
  font-weight: bold;
`;
const Discounts = styled.span`
  font-size: 16px;
  color: #004d40;
`;
