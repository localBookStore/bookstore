import {useState, useEffect} from "react"
import { useHistory, NavLink } from "react-router-dom"
import { useCookies } from "react-cookie"
import axios from "axios"

import { jwtDecode } from "feature/JwtDecode"

import styled from "styled-components"
import { Button } from "react-bootstrap"
import logo from "./icons/bookshop.svg"

const DefaultPage = ({ state, dispatch }) => {
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const [userName, setUserName] = useState(null);
  const history = useHistory();

  useEffect(() => {
    if (cookies.token !== undefined){
      const {nickName} = jwtDecode(cookies.token)
      setUserName(nickName)
    }
  }, [cookies.token])

  const goHome = () => {
    dispatch(!state)
    history.push("/")
  }
  const LogoutEvent = () => {
    const token = cookies.token

    axios.post("http://localhost:8080/logout", null, {
      headers: {
        Authorization: token
      }
    })
      .then(() => removeCookie('token'))
      .catch(err => Comment.log("에러"))

    goHome()
  }

  return <DefaultContainer>
    <ImageButton onClick={() => goHome()}>
      <ImageLogo src={logo} alt="logo" />
    </ImageButton>
    {cookies.token !== undefined ?
      <>
        <IsLoginedDiv>{userName}<span style={{fontSize:"14px"}}> 님 안녕하세요</span></IsLoginedDiv>
        <NavLink to={{pathname:"/mypage", state:{token:cookies.token}}}><MyPageButton variant="success">마이페이지</MyPageButton></NavLink>
        <NavLink to={{pathname:"/cart", state:{token:cookies.token}}}><CartButton variant="primary">장바구니</CartButton></NavLink>
        <LogoutButton variant="danger" onClick={LogoutEvent}>로그아웃</LogoutButton>
      </>
      :
      <>
        <NavLink to='/login' replace><AuthButton variant="outline-info" right="160px" width="105px">Log In</AuthButton></NavLink>
        <NavLink to='/signup' replace><AuthButton variant="outline-info" right="30px" width="100px">Sign Up</AuthButton></NavLink>
      </>
    }
  </DefaultContainer >
}
export default DefaultPage;


const DefaultContainer = styled.div`
  position: relative;
  width: 100%;
`
const ImageButton = styled.button`
  position: relative;
  display: block;
  margin: 0 auto;
  
  border: 0 none;
  background-color: transparent;
`
const AuthButton = styled(Button)`
  position: absolute;
  top: 0;
  right: ${props => props.right};

  border: 2px solid;
  width: ${props => props.width};
  font-size: 18px;
  font-weight: 800;
`
const IsLoginedDiv = styled.div`
  position:absolute;
  top: 50px;
  right: 10px;
  font-size: 20px;
`
const LogoutButton = styled(Button)`
  position:absolute;
  top:0;
  right:20px;
`
const CartButton = styled(Button)`
  position:absolute;
  top:0;
  right:119px;
`
const MyPageButton = styled(Button)`
  position:absolute;
  top:0;
  right:220px;
`
const ImageLogo = styled.img`
  margin: 0 auto;
  width: 200px;
  height: auto;
  overflow: hidden;
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transform: scale(1);
  -webkit-transition: 0.3s;
  -moz-transition: 0.3s;
  -ms-trasition: 0.3s;
  -o-transition: 0.3s;
  transition: 0.3s;

  &:hover {
    -webkit-transform: scale(1.3);
    -moz-transform: scale(1.3);
    -ms-transform: scale(1.3);
    -o-transform: scale(1.3);
    transform: scale(1.3);
  }
`