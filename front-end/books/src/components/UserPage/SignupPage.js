import { useRef } from "react"
import { useForm } from "react-hook-form";
import axios from "axios"

import { Button } from "react-bootstrap"
import styled from "styled-components"


const SignupPage = ({history}) => {
  const { register, handleSubmit, errors, watch } = useForm();
  const password = useRef({});
  password.current = watch("password", "");

  const onSummitEvent = userInfo => {
    const { email, password, nickName } = userInfo
    axios.post("http://localhost:8080/api/signup/", {
      email,
      password,
      nickName
    })
    .then(res => history.replace("/"))
    .catch(err => console.log(err.response))
  }

  return <SignupContainer>
    <SignupTitle>회원가입</SignupTitle>
    <form onSubmit={handleSubmit(onSummitEvent)}>
      <SignupName>이메일</SignupName>
      <SignupInput
        type="text"
        name="email"
        ref={register({
          required: 'this is a required',
          pattern: {
            value: /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
            message: '올바른 형식의 이메일을 입력하세요!',
          },
        })}
      />
      <CheckEmail>확인</CheckEmail>
      <div>{errors.email && errors.email.message}</div>

      <SignupName>닉네임</SignupName>
      <SignupInput
        type="text"
        name="nickName"
        ref={register({
          required: 'this is a required',
        })}
      />
      <div>{errors.nickName && errors.nickName.message}</div>

      <SignupName>패스워드</SignupName>
      <SignupInput
        type="password"
        name="password"
        ref={register({
          required: 'this is a required',
          minLength: {
            value: 8,
            message: "비밀번호는 8자 이상으로 입력하세요."
          },
          pattern: {
            value: /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/,
            message: '비밀번호는 숫자, 문자, 특수문자의 조합으로 생성해주세요/',
          },
        })}
      />
      <div>{errors.password && errors.password.message}</div>

      <SignupName>패스워드2</SignupName>
      <SignupInput
        type="password"
        name="password2"
        ref={register({
          required: 'this is a required',
          validate: value =>
          value === password.current || "비밀번호가 일치하지 않습니다."
        })}
      />
      <div>{errors.password2 && errors.password2.message}</div>
      <button type="submit">버튼</button>
    </form>

  </SignupContainer>
}
export default SignupPage;


const SignupContainer = styled.div`
  width: 100%;
  display: block;
  margin: 10px auto;
`
const SignupTitle = styled.h2`
  margin: 40px 0;
  text-align: center;
`
const SignupName = styled.div`
`

const SignupInput = styled.input`
  display: inline-block;
`
const SignupButton = styled(Button)`
  display: block;
  margin: 20px;
`
const CheckEmail = styled(Button)`
  margin: 0 20px;
`