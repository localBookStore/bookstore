import axios from "axios";
import { useState, useEffect } from "react";
import styled from "styled-components";

const UserInfo = ({ location }) => {
  const [user, setUser] = useState(null);
  useEffect(() => {
    getUserInfo();
  }, []);

  const getUserInfo = () => {
    axios.get("http://localhost:8080/api/mypage", {
        headers: { Authorization: location.state.token }})
      .then((res) => setUser(res.data))
      .catch((err) => console.log(err.response));
  };

  return <Container>
    {user && <div>
        <div>유저이름: {user.nickName}</div>
        <div>유저 이메일: {user.email}</div>
    </div>}      
  </Container>;
};
export default UserInfo;

const Container = styled.div`
    margin: 20px;
`