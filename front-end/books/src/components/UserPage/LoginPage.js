import { useCookies } from "react-cookie"
import { Link } from "react-router-dom"
import { useForm } from "react-hook-form"
import axios from "axios"

import {Avatar, Button, CssBaseline, TextField, Typography, Container} from '@material-ui/core';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import styled from "styled-components"

const GOOGLE_LOGIN_URL = 'http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000/oauth';
const NAVER_LOGIN_URL = 'http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:3000/oauth';
const KAKAO_LOGIN_URL = 'http://localhost:8080/oauth2/authorization/kakao?redirect_uri=http://localhost:3000/oauth';


const LoginPage = ({ history }) => {
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
            label="Email Address"
            autoFocus
            {...register("email")}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            label="Password"
            type="password"
            {...register('password')}
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
        
        <LinkContainer>
          <HrefLink href={GOOGLE_LOGIN_URL} style={{textDecoration: "none"}}>
              <ImageIcon src='https://kgo.googleusercontent.com/profile_vrt_raw_bytes_1587515358_10512.png' alt='googleIcon' />
              <IconName>GOOGLE LOGIN</IconName>
          </HrefLink>
          <HrefLink href={NAVER_LOGIN_URL} style={{textDecoration: "none"}}>
              <ImageIcon src='https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM' alt='naverIcon' />
              <IconName>NAVER LOGIN</IconName>
          </HrefLink>
          <HrefLink href={KAKAO_LOGIN_URL} style={{textDecoration: "none"}}>
              <ImageIcon src='https://t1.kakaocdn.net/kakaocorp/corp_thumbnail/Kakao.png' alt='kakaoIcon' />
              <IconName>KAKAO LOGIN</IconName>
          </HrefLink>
      </LinkContainer>
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
const ImageIcon = styled.img`
  margin-right: 20px;
  width: 30px;
`
const IconName = styled.span`
  font-size: 1rem;
  font-weight: bold;
  color: black;
`
const HrefLink = styled.a`
  width: 60%;
  padding: 5px 10px;
  margin: 5px 0;
  box-shadow: 2px 2px 2px 2px #bdbdbd;
  
`
const LinkContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
`