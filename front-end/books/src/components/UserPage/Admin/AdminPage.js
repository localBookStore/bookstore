import UserList from "./UserList";
import AllItemList from "./AllItemList";
import AllOrderList from "./AllOrderList";
import { useCookies } from "react-cookie";
import { Route, Switch, NavLink } from "react-router-dom";

import styled from "styled-components";

const AdminPage = ({ match }) => {
  const [cookies] = useCookies(["token"]);
  const token = cookies.token;

  const URL = match.url;
  return (
    <Container>
      <NavLink to={{ pathname: `${URL}/userlist`, state: { token } }}>
        회원 리스트
      </NavLink>
      <NavLink to={{ pathname: `${URL}/allitemlist`, state: { token } }}>
        상품 리스트
      </NavLink>
      <NavLink to={{ pathname: `${URL}/allorderedlist`, state: { token } }}>
        주문 리스트
      </NavLink>
      <Switch>
        <Route path={`${URL}/userlist`} component={UserList} />
        <Route path={`${URL}/allitemlist`} component={AllItemList} />
        <Route path={`${URL}/allorderedlist`} component={AllOrderList} />
      </Switch>
    </Container>
  );
};
export default AdminPage;

const Container = styled.div``;
