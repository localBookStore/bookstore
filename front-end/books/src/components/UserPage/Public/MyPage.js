import UserInfo from "./UserInfo";
import OrderList from "./OrderComponent/OrderList";
import MyPost from "./MyPost";
import axios from "axios";
import { useState } from "react";
import { useHistory, Route, Switch, NavLink } from "react-router-dom";
import { useCookies } from "react-cookie";

import { MenuList, MenuItem } from "@material-ui/core"
import { Button, Modal } from "react-bootstrap";
import styled from "styled-components";

const MyPage = ({ match }) => {
  const history = useHistory();
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const token = cookies.token;
  const [show, setShow] = useState(false);
  const [password, setPassword] = useState("");

  const modalOpen = () => setShow(true);
  const modalClose = () => setShow(false);

  const deleteAccount = (e) => {
    modalClose();
    axios
      .post(
        "http://localhost:8080/api/withdrawal",
        { password },
        { headers: { Authorization: token } }
      )
      .then(() => {
        removeCookie("token");
        history.replace("/");
      })
      .catch((err) => console.log(err.response));
  };

  const { path } = match;
  return (
    <Container>
      <MenuListStyled>
        <MenuItem component={NavLink} to={{ pathname: `${path}/userinfo`, state:{ token}}}>
          회원정보
        </MenuItem>
        
        <MenuItem component={NavLink} to={{ pathname: `${path}/orderlist`, state:{ token }}}>
          구매내역
        </MenuItem>

        <MenuItem component={NavLink} to={{ pathname: `${path}/mypost`, state: { token }}}>
          쓴 글보기
        </MenuItem>
        <MenuItem onClick={modalOpen}>
          회원탈퇴
        </MenuItem>
      </MenuListStyled>
      <Switch>
        <Route path={`${path}/userinfo`} component={UserInfo} />
        <Route path={`${path}/orderlist`} component={OrderList} />
        <Route path={`${path}/mypost`} component={MyPost} />
      </Switch>

      <Modal show={show} onHide={modalClose} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>정말로 탈퇴하겠습니까?</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          탈퇴시 개인정보와 함께 모든 이용내역이 지워집니다.
          <br />
          마지막으로 PASSWORD를 입력하시고 회원 탈퇴를 눌러주세요.
          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={deleteAccount}>
            회원탈퇴
          </Button>
          <Button variant="secondary" onClick={modalClose}>
            취소
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};
export default MyPage;

const Container = styled.div`
  display: flex;
  justify-content: space-between;
`;

const MenuListStyled = styled(MenuList)`
  margin: 30px 0;
`
