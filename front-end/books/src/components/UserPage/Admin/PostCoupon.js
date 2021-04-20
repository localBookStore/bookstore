import axios from "axios";
import { useForm } from "react-hook-form";

import { Button } from "react-bootstrap"
import styled from "styled-components";

const PostCoupon = ({location}) => {
	const { register, handleSubmit, errors } = useForm();
  const token = location.state.token;
	const submitEvent = data => {
    axios.post("api/coupon",{ ...data },{ headers:{ Authorization: token }})
      .then(res => alert("모든 사용자에게 쿠폰이 발급되었습니다."))
      .catch(err => console.log(err.response))
  };

	return (
		<Container>
			<form onSubmit={handleSubmit(submitEvent)}>
				<div>원하는 쿠폰 이름을 입력하세요. </div>
				<Input type="text" placeholder="쿠폰 이름" name="name" ref={register({required: true, maxLength: 80 })} />
        <div>{errors.name && "쿠폰이름은 필수이며 80자 이내로 입력하세요"}</div>
				<div>쿠폰에 대한 설명을 입력하세요. </div>
				<Input type="text" placeholder="쿠폰 설명" name="description" ref={register({required:true})} />
        <div>할인율(%)을 입력하세요. (숫자만 입력)</div>
        <Input type="text" placeholder="할인율" name="discountRate" ref={register({required:true, pattern:/^[0-9]*$/})}/>
        <div>{errors.discountRate && "할인율은 필수이며 숫자만 입력하세요"}</div>
        <div>종료일자를 입력하세요(년월일기준 "-" 기재). (ex. 2020-02-02) </div>
        <Input type="text" placeholder="쿠폰 종료일자" name="endDate" ref={register()}/>
        <div>{errors.endDate && "쿠폰종료 일자는 필수이며 연-월-일로 입력하세요"}</div>
        <SubmitButton type="submit">등록(모든 사용자에게 일괄제공)</SubmitButton>
        
			</form>
		</Container>
	);
};
export default PostCoupon;

const Container = styled.div``;
const Input = styled.input``;
const SubmitButton = styled(Button)`
  margin: 20px 0;
`