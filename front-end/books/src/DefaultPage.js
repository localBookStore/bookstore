import { useState, useEffect } from "react";
import { NavLink, useHistory } from "react-router-dom";
import { useCookies } from "react-cookie";
import axios from "axios";

import { jwtDecode } from "feature/JwtDecode";

import styled from "styled-components";
import { Button } from "@material-ui/core";
import logo from "./icons/bookshop.svg";

const DefaultPage = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const history = useHistory();
  const [user, setUser] = useState({
    name: "",
    role: false,
  });

  useEffect(() => {
    if (cookies.token !== undefined) {
      const { nickName, role } = jwtDecode(cookies.token);
      setUser({
        ...user,
        name: nickName,
        role,
      });
    }
  }, [cookies.token]);

  const goHome = () => {
    // window.location.replace("/")
    history.push('/');
  };

  const logoutEvent = () => {
    const token = cookies.token;
    axios.post("logout", null, {
        headers: { Authorization: token },
      })
      .then(() => {
        goHome()
        removeCookie("token");
      })
      .catch(() => alert("다시 한번 로그아웃 해주세요"));
  };

  const RegularUser = () => {
    return <>
        <LoginedDiv>
          <NameDiv fontSize="20px" fontWeight="700">{user.name}</NameDiv>
          <NameDiv> 님 안녕하세요</NameDiv>
        </LoginedDiv>
        <UserButtons>
          <UserButton 
            variant="outlined" 
            color="primary" 
            component={NavLink}
            to={{ pathname: "/mypage", state: { token: cookies.token } }}
            // right="17vw"
            >
            My Page
          </UserButton>

          <UserButton 
            variant="outlined" 
            color="primary" 
            component={NavLink} 
            to={{ pathname: "/cart", state: { token: cookies.token } }}
            // right="9vw"
            >
            My Cart
            </UserButton>
          
          <UserButton 
            variant="outlined" 
            color="primary" 
            onClick={logoutEvent}
            // right="1vw"
            >
            Logout
          </UserButton>
        </UserButtons>
      </>
  };

  const AdminUser = () => {
    return <>
      <LoginedDiv>
          <NameDiv fontSize="20px" fontWeight="700">{user.name}</NameDiv>
          <NameDiv> 님 안녕하세요</NameDiv>
        </LoginedDiv>
        <NavLink to={{ pathname: "/admin", state: { token: cookies.token } }}>
          <AdminButton variant="outlined" color="primary">관리 페이지</AdminButton>
        </NavLink>
        <AdminButton variant="outlined" color="primary" onClick={logoutEvent}>
          로그아웃
        </AdminButton>
      </>
  };

  return (
    <DefaultContainer>
      <ImageButton onClick={goHome}>
        <ImageLogo src={logo} alt="logo" />
      </ImageButton>
      {cookies.token !== undefined ? (
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
  
  font-size: 16px;
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
  font-size: ${props => props.fontSize || "12px"};
  font-weight: ${props => props.fontWeight || "300px" };
`;
const AdminButton = styled(Button)`
  position: absolute;
  top: 0;
  right: 119px;
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
  width: 20vw;
  display: flex;
  justify-content: space-between;
  right: 0;
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
