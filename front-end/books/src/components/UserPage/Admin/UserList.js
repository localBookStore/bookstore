import axios from "axios";
import { useState, useEffect } from "react";

import styled from "styled-components";

const UserList = ({ location }) => {
  const token = location.state.token;
  console.log(token);
  const [userList, setUserList] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/admin/members/", {
        headers: {
          Authorization: token,
        },
      })
      .then(res => setUserList(res.data))
      .catch((err) => console.log(err.response));
  }, []);

  return <Container>
    <ContainerTitle>회원 리스트입니다.</ContainerTitle>
    {userList && userList.map(user => {
      return <button>{user.nickName}<br />{user.email}</button>
    })}

  </Container>;
};
export default UserList;

const Container = styled.div``;
const ContainerTitle = styled.h2`
  margin: 30px 0;
`