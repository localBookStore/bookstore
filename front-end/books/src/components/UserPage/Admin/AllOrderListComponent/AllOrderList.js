import { useState, useEffect } from "react";
import axios from "axios";
import UserOrder from "./UserOrder"

import { Button } from "@material-ui/core"
import styled from "styled-components";

const AllOrderList = ({ location, match }) => {
	const [users, setUsers] = useState([]);
  const [selectIdx, setSelectIdx] = useState(-1);
	const token = location.state.token;
  const URL = match.url
  
	useEffect(() => {
		axios.get("api/admin/members", { headers: { Authorization: token } })
      .then(res => setUsers(res.data))
			.catch(err => console.log(err.response));
	}, []);
  
	return <Container>
    <ContainerTitle>👨‍👩‍👧‍👦📦 각 회원들의 주문 내용입니다.</ContainerTitle>
    <div  style={{display:"flex", justifyContent:"flex-start"}}>
      <UserButtons>
      {users.length ? users.map((user, idx) => {
        return <UserButton 
        onClick={() => setSelectIdx(idx)}
        variant="outlined"
        key={idx} 
        >{user.email}<br/>{user.nickName}
      </UserButton>
      }) : <NotUser>😭 등록된 회원들이 없습니다</NotUser>}
      </UserButtons>

      {selectIdx > -1 && <UserOrder userInfo={users[selectIdx]} />}
    </div>

  </Container>;
};
export default AllOrderList;

const Container = styled.div`

`;
const UserButtons = styled.div`
  display: flex;
  flex-direction: column;
`
const UserButton = styled(Button)`
  margin:10px 0;
  text-transform: none;

  color: #009688;
  background-color: white;

  &:hover {
    color: white;
    background-color: #4db6ac;
  }

`
const NotUser = styled.div`
  font-size: 24px;
`

const ContainerTitle = styled.div`
  margin: 30px 20px;
  font-size: 26px;
  font-weight: bold;
`