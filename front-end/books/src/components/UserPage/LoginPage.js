import { useState } from "react"
import { useCookies } from "react-cookie"
import axios from "axios"
import GoogleButton from "./GoogleButton.js"

import { Button } from "react-bootstrap"
import styled from "styled-components"


export const doLogin = (history, userInfo, dispatch) => {
  axios.post("http://localhost:8080/login", userInfo)
    .then(res => {
      const token = res.headers.authorization
      dispatch("token", token)
      history.replace("/")
    })
    .catch(() => alert("아이디 혹은 비밀번호가 틀렸습니다."))
  }

export const socialLogin = (provider) => {
  // console.log(window.location.href)
  window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`
  return null;
  // history.push(`/oauth2/authorization/${provider}`)
}

const LoginPage = ({ history }) => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [userInfo, setUserInfo] = useState({
    email: "",
    password: ""
  });

  const clickEvent = () => {
    doLogin(history, userInfo, setCookie)
  }

  const changeEvent = event => {
    const { name, value } = event.target
    setUserInfo({
      ...userInfo,
      [name]: value
    })
  }
  const pressEvent = e => {
    if (e.key === "Enter") {
      clickEvent()
    }
  }
  return <LoginContainer>
    <LoginTitle>로그인</LoginTitle>
    <LoginDiv>아이디<LoginInput type="text" name="email" onChange={e => changeEvent(e)} /></LoginDiv>
    <LoginDiv>비밀번호<LoginInput type="password" name="password"
      onChange={e => changeEvent(e)}
      onKeyPress={pressEvent}
    /></LoginDiv>
    <LoginButton varirant="primary"
      onClick={clickEvent}
    >로그인</LoginButton>
    {/* <Button variant="light" onClick={() => doGoogleLogin("google")}>구글 로그인</Button> */}
    <GoogleButton>구글 로그인</GoogleButton>
    <Button variant="light" onClick={() => socialLogin("naver")}>네이버 로그인</Button>
    <Button variant="light" onClick={() => socialLogin("kakao")}>카카오 로그인</Button>
  </LoginContainer>
}
export default LoginPage;

const LoginContainer = styled.div`
  margin: 30px;
`
const LoginTitle = styled.h2`
  display: block;
  margin: 15px 0;
  text-align: center;

  font-size: 40px;
  font-weight: 800;
`
const LoginInput = styled.input`
  display: block;
`
const LoginDiv = styled.div`
  margin: 20px 0;
`
const LoginButton = styled(Button)`
  font-weight: 800;
  font-size: 14px;
`