import { useState, useEffect } from "react"
import axios from "axios"

import { Button } from "react-bootstrap"
import styled from "styled-components"
import Header from "../HeaderPage/Header"

const LoginPage = () => {
  const [userInfo, setUserInfo] = useState({
    email: "",
    password: ""
  })
  const clickEvent = () => {
    console.log(userInfo)
    axios.post("http://localhost:8080/login", userInfo, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
        "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token"
      }
    })
      .then(res => console.log(res))
      .catch(err => console.log(err.response))
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