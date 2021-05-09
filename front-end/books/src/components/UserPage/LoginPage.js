import { useCookies } from "react-cookie"
import { Link } from "react-router-dom"
import { useForm } from "react-hook-form"
import axios from "axios"

import {Avatar, Button, CssBaseline, TextField, Typography, Container} from '@material-ui/core';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import styled from "styled-components"

const GOOGLE_LOGIN_URL = 'http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000/oauth';

const LoginPage = ({ history, location }) => {
  const { register, handleSubmit } = useForm();
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);

  
  const submitEvent = (data) => {
    axios.post("login", data)
      .then(res => {
        const token = res.headers.authorization
        setCookie("token", token)
        history.replace("/")
      })
      .catch(() => alert("아이디 혹은 비밀번호가 틀렸습니다."))
    }

  return <FormContainer component="main" maxWidth="xs">
      <CssBaseline />
      <IconDiv>
        <Avatar><LockOutlinedIcon /></Avatar>
        <Typography component="h1" variant="h3">Log In</Typography>
        <form onSubmit={handleSubmit(submitEvent)}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoFocus
            inputRef={register}
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
            inputRef={register}
          />
          <SubmitButton
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
          >
            Sign In
          </SubmitButton>
          <Typography variant="body2" align="center"><Link to="/signup" variant="body1">Sign Up</Link></Typography>
        </form>
      </IconDiv>
      <a href={GOOGLE_LOGIN_URL}>구글 로그인</a>
    </FormContainer>
}
export default LoginPage;

const IconDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const FormContainer = styled(Container)`
  margin: 5% auto;
`
const SubmitButton = styled(Button)`
height: 40px;
  &&{
    margin: 5% 0;
  }
`