import { useCookies } from "react-cookie"
import { Link } from "react-router-dom"
import { useForm } from "react-hook-form"
import axios from "axios"

import {Avatar, Button, CssBaseline, TextField, Grid, Typography, Container} from '@material-ui/core';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import styled from "styled-components"


const LoginPage = ({ history }) => {
  const { register, handleSubmit } = useForm();
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  

  const submitEvent = (data) => {
    axios.post("http://localhost:8080/login", data)
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
    </FormContainer>
}
export default LoginPage;

const IconDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const FormContainer = styled(Container)`
  margin: 5%;
`
const SubmitButton = styled(Button)`
height: 40px;
  &&{
    margin: 5% 0;
  }
`