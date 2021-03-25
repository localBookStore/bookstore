import { useState } from "react"
import { useHistory, NavLink } from "react-router-dom"

import styled from "styled-components"
import { Button } from "react-bootstrap"
import logo from "./icons/bookshop.svg"

const DefaultPage = ({ state, dispatch }) => {
  const [isLogin, setIsLogin] = useState(false);
  const history = useHistory();

  const goHome = () => {
    dispatch(!state)
    history.push("/")
  }

  return <DefaultContainer>
    <ImageButton onClick={() => goHome()}>
      <ImageLogo src={logo} alt="logo" />
    </ImageButton>
    {isLogin ? <div>로그인 됨</div>
      :
      <>
        <NavLink to='/login'><AuthButton variant="outline-info" right="160px" width="105px">Log In</AuthButton></NavLink>
        <NavLink to='/signup'><AuthButton variant="outline-info" right="30px" width="100px">Sign Up</AuthButton></NavLink>
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