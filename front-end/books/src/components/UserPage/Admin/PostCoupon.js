import axios from "axios";
import { useForm } from "react-hook-form";
import { useCookies } from "react-cookie" 

import { Button, TextField } from "@material-ui/core"
import styled from "styled-components";

const PostCoupon = () => {
	const { register, handleSubmit, errors } = useForm();
  const [cookies] = useCookies(["token"])
  const token = cookies.token;
	const submitEvent = data => {
    axios.post("api/coupon",{ ...data },{ headers:{ Authorization: token }})
      .then(() => alert("모든 사용자에게 쿠폰이 발급되었습니다."))
      .catch(err => console.log(err.response))
  };

	return (
		<Container>
      <CouponTitle>🎫 쿠폰 등록하기</CouponTitle> 
			<form onSubmit={handleSubmit(submitEvent)}>
				<ContentDiv>원하는 쿠폰 이름을 입력하세요. (80자 이내) </ContentDiv>
				<Input variant="outlined" placeholder="쿠폰 이름" name="name" inputRef={register({required: true, maxLength: 80 })} />
        <div>{errors.name && "쿠폰이름은 필수이며 80자 이내로 입력하세요"}</div>
				<ContentDiv>쿠폰에 대한 설명을 입력하세요. </ContentDiv>
				<Input variant="outlined" placeholder="쿠폰 설명" name="description" inputRef={register({required:true})} />
        <ContentDiv>할인율(%)을 입력하세요. (숫자만 입력)</ContentDiv>
        <Input variant="outlined" placeholder="할인율" name="discountRate" inputRef={register({required:true, pattern:/^[0-9]*$/})}/>
        <div>{errors.discountRate && "할인율은 필수이며 숫자만 입력하세요"}</div>
        <ContentDiv>종료일자를 입력하세요(년월일기준 "-" 기재). (ex. 2020-02-02) </ContentDiv>
        <Input variant="outlined" placeholder="쿠폰 종료일자" name="endDate" inputRef={register()}/>
        <div>{errors.endDate && "쿠폰종료 일자는 필수이며 연-월-일로 입력하세요"}</div>
        <SubmitButton type="submit" variant="contained" color="primary">등록(모든 사용자에게 일괄제공)</SubmitButton>
        
			</form>
		</Container>
	);
};
export default PostCoupon;

const Container = styled.div`
  margin: 20px;
`;
const Input = styled(TextField)`
  width: 40vw;
`;
const ContentDiv= styled.div`
  font-size: 20px;
  margin: 10px 10px;
`

const SubmitButton = styled(Button)`
  margin: 30px 0px;
  font-size: 18px;
`
const CouponTitle = styled.div`
  font-size: 26px;
  font-weight: bolder;
  margin: 20px 0;
`