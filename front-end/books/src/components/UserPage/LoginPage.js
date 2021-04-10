import { useState } from "react"
import { useCookies } from "react-cookie"
import axios from "axios"
import GoogleButton from "./GoogleButton.js"

import {Avatar, Button, CssBaseline, TextField, FormControlLabel, Link, Grid, Box, Typography, Container} from '@material-ui/core';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
// import { Button } from "react-bootstrap"
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
  return <Container component="main" maxWidth="xs">
      <CssBaseline />
      <IconDiv>
        <Avatar><LockOutlinedIcon /></Avatar>
        <Typography component="h1" variant="h5">
          Log In
        </Typography>
        <form >
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            autoFocus
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
          >
            Sign In
          </Button>
          <Grid container>
            <Grid item xs>
              <Link href="#" variant="body2">
                Forgot password?
              </Link>
            </Grid>
            <Grid item>
              <Link href="#" variant="body2">
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
        </form>
      </IconDiv>
    </Container>
  {/* // return <LoginContainer>
  //   <LoginTitle>로그인</LoginTitle>
  //   <LoginDiv>아이디<LoginInput type="text" name="email" onChange={e => changeEvent(e)} /></LoginDiv>
  //   <LoginDiv>비밀번호<LoginInput type="password" name="password"
  //     onChange={e => changeEvent(e)}
  //     onKeyPress={pressEvent}
  //   /></LoginDiv>
  //   <LoginButton varirant="primary"
  //     onClick={clickEvent}
  //   >로그인</LoginButton>
  //   </LoginContainer> */}
}
export default LoginPage;

const IconDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`