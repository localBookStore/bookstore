import axios from "axios";
import { useState, useEffect } from "react";
import UserArticle from "./UserArticle"

import { Button } from "react-bootstrap"
import styled from "styled-components";

const UserList = ({ location }) => {
  const { token } = location.state;
  const [userList, setUserList] = useState([]);
  const [selectIdx, setSelectIdx] = useState(-1);

  useEffect(() => {
    axios.get("api/admin/members/", {
      headers: {
        Authorization: token,
      }
    })
    .then(res => setUserList(res.data))
    .catch((err) => console.log(err.response));
  }, []);

  return <Container>
    <ContainerTitle>회원 리스트입니다.</ContainerTitle>
    {userList.length && userList.map((user, idx) => {
      return <Button 
        onClick={() => setSelectIdx(idx)}
        variant="info" 
        key={idx} 
        >{user.nickName}<br />{user.email}</Button>
    })}
    {userList.length && selectIdx > -1 && 
      <UserArticle userInfo={userList[selectIdx]} />}

  </Container>;
};
export default UserList;

const Container = styled.div``;
const ContainerTitle = styled.h2`
  margin: 30px 0;
`