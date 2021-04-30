import UserList from "./UserList";
import AllItemList from "./AllItemList";
import AllOrderList from "./AllOrderListComponent/AllOrderList";
import PostCoupon from "./PostCoupon";
import { useCookies } from "react-cookie";
import { Route, Switch, NavLink } from "react-router-dom";
import Sticky from "react-sticky-el"

import PanToolTwoToneIcon from '@material-ui/icons/PanToolTwoTone';
import { MenuList, MenuItem, Paper,TextField, Button } from "@material-ui/core"
import { Modal } from "react-bootstrap";
import styled, { keyframes } from "styled-components";

const AdminPage = ({ match }) => {
	const [cookies] = useCookies(["token"]);
	const token = cookies.token;

	const { path } = match;
	
	return <Container>
			<MenuListStyled component={Sticky}>
          <StyledPaper elevation={5}>
            <MenuItemStyled component={NavLink} to={{ pathname: `${path}/userlist`, state: { token } }}>
              회원 리스트
            </MenuItemStyled>
            <MenuItemStyled component={NavLink} to={{ pathname: `${path}/allitemlist`, state: { token } }}>
              상품 리스트
            </MenuItemStyled>
            <MenuItemStyled component={NavLink} to={{ pathname: `${path}/allorderedlist`, state: { token } }}>
              주문 리스트
            </MenuItemStyled>
            <MenuItemStyled component={NavLink} to={{ pathname: `${path}/postcoupon`, state: { token } }}>
              쿠폰 등록
            </MenuItemStyled>
          </StyledPaper>
			</MenuListStyled>
			<SwitchDiv>
				<Route path={`${path}/userlist`} component={UserList} />
				<Route path={`${path}/allitemlist`} component={AllItemList} />
				<Route path={`${path}/allorderedlist`} component={AllOrderList} />
				<Route path={`${path}/postcoupon`} component={PostCoupon} />
			</SwitchDiv>
	</Container>
};
export default AdminPage;

const Container = styled.div`
	display: inline-flex;
`;

const MenuListStyled = styled(MenuList)`  
	width: 13vw;
  `
const MenuItemStyled = styled(MenuItem)`
  font-family: 'Jua', sans-serif;
  font-size: 20px;
  font-weight: 500;
  background-color: #ffecb3;

  margin: 5px 0;
`
const StyledPaper = styled(Paper)`

  display: flex;
  flex-direction: column;
  align-items: center;
	
  height: auto;
  border: 1px solid white;
  background-color: #ffecb3;
`
const SwitchDiv = styled.div`
	margin-left: 2vw;
`