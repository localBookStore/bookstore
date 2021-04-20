import { useState, useEffect } from "react";
import { NavLink, Route, Switch} from "react-router-dom";
import axios from "axios";
import UserOrder from "./UserOrder"

import styled from "styled-components";

const AllOrderList = ({ location, match }) => {
	const [users, setUsers] = useState([]);
	const token = location.state.token;
  const URL = match.url
  
	useEffect(() => {
		axios.get("api/admin/members", { headers: { Authorization: token } })
      .then(res => setUsers(res.data))
			.catch(err => console.log(err.response));
	}, []);
  
	return <Container>
    {users.length && users.map((user, idx) => {
      return <UserContainer key={idx}>
        <NavLink exact to={{ pathname:`${URL}/${user.id}`, state:{token, user}}}>
          {user.nickName}({user.email})
        </NavLink>
      </UserContainer> 
    })}  
  <Switch>
    <Route exact path={`${URL}/:id`} component={UserOrder} />
  </Switch>
  </Container>;
};
export default AllOrderList;

const Container = styled.div`

`;
const UserContainer = styled.div`
  font-size: 22px;
  margin: 30px 0;
`