import { useState, useEffect } from "react";
import { NavLink, useHistory } from "react-router-dom";
import { useCookies } from "react-cookie";
import axios from "axios";

import jwtDecode from "feature/jwtDecode";

import styled from "styled-components";
import { Button } from "@material-ui/core";
import logo from "./icons/bookshop.svg";

const DefaultPage = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const token = cookies.token;
  
  const history = useHistory();
  const [user, setUser] = useState({
    name: "",
    role: false,
  });

  useEffect(() => {
    if (token !== undefined) {
      const { nickName, role, sub } = jwtDecode(token);
      setUser({
        ...user,
        name: nickName,
        role,
        email: sub
      });
    }
  }, [token]);

  const goHome = () => {
    window.location.replace("/")
  };

  const logoutEvent = () => {
    axios.post("logout", null, {
        headers: { Authorization: token },
      })
      .then(() => {
        removeCookie("token");
        goHome()
      })
      .catch(() => alert("다시 한번 로그아웃 해주세요"));
  };

  const RegularUser = () => {
    return <>
        <LoginedDiv>
          <NameDiv>로그인 계정: </NameDiv>
          <NameDiv fontSize="1.5vw" fontWeight="700">{user.email}</NameDiv>
        </LoginedDiv>
        <UserButtons>
          <UserButton 
            variant="outlined" 
            color="primary" 
            component={NavLink}
            to={{ pathname: "/mypage", state: { token } }}
            >
            마이페이지
          </UserButton>

          <UserButton 
            variant="outlined" 
            color="primary" 
            component={NavLink} 
            to={{ pathname: "/cart", state: { token } }}
            >
            장바구니
            </UserButton>
          
          <UserButton 
            variant="outlined" 
            color="primary" 
            onClick={logoutEvent}
            >
            로그아웃
          </UserButton>
        </UserButtons>
      </>
  };

  const AdminUser = () => {
    return <>
      <LoginedDiv>
          <NameDiv>로그인 계정:</NameDiv>
          <NameDiv fontSize="1.5vw" fontWeight="700">{user.email}</NameDiv>
        </LoginedDiv>
        <AdminButtons>
          <AdminButton
            component={NavLink}
            to={{pathname: "/admin"}}
            variant="outlined"
            >
            관리페이지
          </AdminButton>
          <AdminButton 
            variant="outlined" 
            color="primary" 
            onClick={logoutEvent}
            >
            로그아웃
          </AdminButton>
        </AdminButtons>
      </>
  };

  return (
    <DefaultContainer>
      <ImageButton onClick={() => history.push('/')}>
        <ImageLogo src={logo} alt="logo" />
      </ImageButton>
      {token !== undefined ? (
        user.role === "USER" ? <RegularUser /> : <AdminUser />
      ) : <AuthButtons>
          <AuthButton 
            variant="outlined" 
            color="secondary" 
            component={NavLink} 
            to="/login"
            right="17vw" 
            >
            Log In
          </AuthButton>
          
          <AuthButton 
            variant="outlined" 
            color="secondary" 
            right="10vw" 
            component={NavLink} 
            to="/signup"
            >
            Sign Up
          </AuthButton> 
        </AuthButtons>
      }
    </DefaultContainer>
  );
};
export default DefaultPage;

const DefaultContainer = styled.div`
  position: relative;
  width: 100%;
`;
const ImageButton = styled.button`
  position: relative;
  display: block;
  margin: 0 auto;

  border: 0 none;
  background-color: transparent;
`;
const AuthButton = styled(Button)`
  padding: 5px;
  width: 6vw;
  
  font-size: 1.1vw;
  font-weight: 600;
`;
const AuthButtons = styled.div`
  position: absolute;
  top: 0;
  right: 0;

  width: 14vw;
  display: flex;
  justify-content: space-between;
`
const LoginedDiv = styled.div`
  position: absolute;
  top: 50px;
  right: 10px;
  font-size: 20px;
`;
const NameDiv = styled.span`
  font-size: ${props => props.fontSize || "1vw"};
  font-weight: ${props => props.fontWeight || "300px" };
`;
const AdminButtons = styled.div`
  position: absolute;
  top: 0;
  right: 0;
  display: flex;
  justify-content: space-between;

  width: 11vw;
`
const AdminButton = styled(Button)`
  color: #3f51b5;
  background-color: white;
  font-size: 1vw;
  font-weight: bold;
  padding: 5px;
  
  &:hover{
    color: white;
    background-color: #3f51b5;
  }
`;
const UserButton = styled(Button)`
  padding: 5px;
  width: 6vw;

  font-weight: bold;
  font-size: 1vw;
`;

const UserButtons = styled.div`
  position: absolute;
  top: 0;
  right: 0;
  width: 20vw;
  display: flex;
  justify-content: space-between;
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
`;
