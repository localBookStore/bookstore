import { doLogin } from "./LoginPage"

import { useState, useRef } from "react"
import { useForm } from "react-hook-form"
import { useCookies } from "react-cookie"
import axios from "axios"

import { Button } from "react-bootstrap"
import styled from "styled-components"


const SignupPage = ({ history }) => {
  const [cookies, setCookie] = useCookies(['token']);
  const { register, handleSubmit, errors, watch } = useForm();
  const [isCheck, setIsCheck] = useState({
    overlab: false,
    checkCode: false,
  })

  const EMAIL = useRef(null);
  const PASSWORD = useRef(null);
  const AUTHCODE = useRef(null);
  EMAIL.current = watch("email", "");
  PASSWORD.current = watch("password", "");
  AUTHCODE.current = watch("authCode", "");

  const doSignup = data => {
    const { email, password, nickName } = data
    const { overlab, checkCode } = isCheck

    if (overlab && checkCode) {
      axios.post("http://localhost:8080/api/signup/", {
        email,
        password,
        nickName
      })
        .then(res => {
          history.replace("/login")
        })
        .catch(err => console.log("에러"))
    } else {
      console.log("중복확인과 이메일 인증을 마무리 하세요")
    }
  }

  const sendEmailCode = () => {
    axios.post("http://localhost:8080/api/signup/request-certificated", {
      email: EMAIL.current
    })
      .then(res => console.log("이메일을 보냈습니다."))
      .catch(err => console.log(err.response))
  }

  const checkEmailCode = () => {
    axios.post("http://localhost:8080/api/signup/check-certificated", {
      certificated: AUTHCODE.current
    })
      .then(() => {
        setIsCheck({
          ...isCheck,
          checkCode: true
        })
        console.log("인증되었습니다.")
      })
      .catch(err => console.log(err.response))
  }

  const checkOverlab = () => {
    axios.post("http://localhost:8080/api/signup/duplicated", {
      "email": EMAIL.current
    })
      .then(res => {
        setIsCheck({
          ...isCheck,
          overlab: true
        })
        console.log(res.data)
      })
      .catch(err => console.log(err.response.data.message))
  }

  return <SignupContainer>
    <SignupTitle>회원가입</SignupTitle>

    <form onSubmit={handleSubmit(doSignup)}>
      <SignupName>이메일</SignupName>
      <SignupInput
        type="text"
        name="email"
        readOnly={isCheck.overlab}
        ref={register({
          required: 'this is a required',
          pattern: {
            value: /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
            message: '올바른 형식의 이메일을 입력하세요!',
          },
        })}
      />
      <CheckButton onClick={() => checkOverlab()}>중복확인</CheckButton>
      <span>{errors.email && errors.email.message}</span>
      <SendEmailCodeButton variant="secondary" onClick={sendEmailCode}>인증 코드 보내기</SendEmailCodeButton>
      <SignupName>인증코드입력</SignupName>
      <SignupInput
        type="password"
        name="authCode"
        readOnly={isCheck.checkCode}
        ref={register({ required: "값이 필요합니다" })}
      /><CheckButton variant="secondary" onClick={checkEmailCode}>인증코드확인</CheckButton>


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
            value === PASSWORD.current || "비밀번호가 일치하지 않습니다."
        })}
      />
      <div>{errors.password2 && errors.password2.message}</div>

      <SignupSummitButton variant="success" type="submit">회원가입</SignupSummitButton>
    </form>


  </SignupContainer>
}
export default SignupPage;

const SendEmailCodeButton = styled(Button)`
  display:block;
`

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

const SignupSummitButton = styled(Button)`
  display: block;
  margin: 20px;
`
const CheckButton = styled(Button)`
  margin: 0 20px;
`