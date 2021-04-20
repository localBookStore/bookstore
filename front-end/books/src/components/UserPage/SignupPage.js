import { useState, useRef } from "react"
import { useForm } from "react-hook-form"
import axios from "axios"

import {Avatar, Button, Input, CssBaseline, TextField, CircularProgress, Typography, Container} from '@material-ui/core';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';

import styled from "styled-components"


const SignupPage = ({ history }) => {
  const [isLoading, setIsLoading] = useState(false)
  const { register, handleSubmit, errors, watch } = useForm();
  const [isCheck, setIsCheck] = useState({
    overLab: "",
    checkCode: "",
  })

  const EMAIL = useRef(null);
  const PASSWORD = useRef(null);
  const AUTHCODE = useRef(null);
  const NICKNAME = useRef(null);
  const BIRTH = useRef(null);
  EMAIL.current = watch("email", "");
  PASSWORD.current = watch("password", "");
  AUTHCODE.current = watch("authCode", "");
  NICKNAME.current = watch("nickName", "");
  BIRTH.current = watch("birth", "");

  const doSignup = data => {
    const { email, password, nickName, birth } = data
    const { overLab, checkCode } = isCheck

    if (overLab && checkCode) {
      axios.post("api/signup/", {
        email,
        password,
        nickName,
        birth,
      })
      .then(() => history.replace("/login"))
      .catch(() => console.log("에러"))
    } 
    else alert("중복확인과 이메일 인증을 마무리 하세요")
  }

  const sendEmailCode = () => {
    setIsLoading(true)
    axios.post("api/signup/request-certificated", {
      email: EMAIL.current
    })
      .then(() => {
        setIsLoading(false)
        alert("이메일에 인증코드를 전송하였습니다.")
      })
      .catch(err => setIsLoading(false))
  }

  const checkEmailCode = () => {
    console.log(AUTHCODE.current)
    axios.post("api/signup/check-certificated", {
      certificated: AUTHCODE.current
    })
    .then(() => setIsCheck({
        ...isCheck,
        checkCode: true
    }))
      .catch(() => alert("인증 코드가 다릅니다."))
  }

  const checkOverLab = () => {
    axios.post("api/signup/duplicated", {
      email: EMAIL.current
    })
      .then(() => setIsCheck({
          ...isCheck,
          overLab: EMAIL.current
        }))
      .catch(err => alert(err.response.data.message))
  }

  if (isLoading) { return <Typography variant="h2" align="center"><CircularProgress color="secondary"/></Typography> }
  
  return <FormContainer maxWidth="md">
  <CssBaseline />
  <IconDiv>
    <Avatar><LockOutlinedIcon/></Avatar>
    <Typography component="h2" variant="h3">Sign Up</Typography>
    <form onSubmit={handleSubmit(doSignup)}>
      <TextField
        defaultValue={isCheck.overLab}
        variant="outlined"
        margin="normal"
        required
        fullWidth
        label="Email Address"
        name="email"
        autoFocus
        type="text"
        readOnly={isCheck.overLab}
        inputRef={register({
          required: 'this is a required',
          pattern: {
            value: /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
            message: '올바른 형식의 이메일을 입력하세요!',
          },
        })}
        />
      {isCheck.overLab ? 
        <span>사용가능</span> : 
        <Button color="primary" onClick={checkOverLab}>중복확인</Button>
      }
      <span>{errors.email && errors.email.message}</span>
      {isCheck.overLab ? 
        <Button color="primary" onClick={sendEmailCode}>인증 코드 보내기</Button> : 
        <Typography display="inline" variant="body2">인증확인필요</Typography> 
      }
      <div>
        <TextField 
          style={{marginLeft:"10px"}} 
          name="authCode" 
          inputRef={register()} 
          placeholder={isCheck.overLab ? "인증코드 입력" : "중복확인을 하세요"} 
          disabled={isCheck.checkCode}
        />

        {!isCheck.checkCode ? <Button color="secondary" onClick={checkEmailCode}>인증 확인하기</Button> : <span>인증확인</span>}
      </div>

      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        name="password"
        label="Password"
        type="password"
        inputRef={register({
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

      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        name="password2"
        label="Password Confirm"
        type="password"
        inputRef={register({
          required: 'this is a required',
          validate: value =>
            value === PASSWORD.current || "비밀번호가 일치하지 않습니다."
          })}
        />
      <div>{errors.password2 && errors.password2.message}</div>
      
      <TextField 
        required
        variant="outlined"
        margin="normal"
        name="nickName"
        label="Nick Name"
        type="text"
        fullWidth
      />
      <TextField 
        required
        variant="outlined"
        margin="normal"
        name="birth"
        label="Birth"
        type="text"
        fullWidth
        placeholder="ex) 930820"
      />

      <SubmitButton
        type="submit"
        fullWidth
        variant="contained"
        color="primary"
      >Sign In</SubmitButton>


    </form>
  </IconDiv>

</FormContainer>
}
export default SignupPage;

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
    margin-top: 5%;
  }
`