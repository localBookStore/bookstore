import axios from "axios"
import { useState } from "react"
import { useHistory } from "react-router-dom"
import { useCookies } from "react-cookie"

import { Button, Modal } from "react-bootstrap"
import styled from "styled-components"

const MyPage = ({ location }) => {
  const history = useHistory();
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const token = cookies.token;
  const [selected, setSelected] = useState("");
  const [show, setShow] = useState(false);
  const [password, setPassword] = useState("");

  const modalOpen = () => setShow(true);
  const modalClose = () => setShow(false);

  const clickEvent = e => {
    setSelected(e.target.name)
  }

  const deleteAccout = e => {
    modalClose()
    axios.post("http://localhost:8080/api/withdrawal", {
      password
    },
      {
        headers: {
          Authorization: token
        }
      })
      .then(() => {
        removeCookie("token")
        history.replace("/")
      })
      .catch(err => console.log(err.response))
  }

  const ShowComponent = () => {
    switch (selected) {
      case "userinfo":
        return <div>유저정보</div>

      case "orderlist":
        return <div>주문내역</div>

      case "mypost":
        return <div>쓴 글 보기</div>

      default:
        return <div>없음</div>
    }
  }

  return <Container>
    <SideBar>
      <Button name="userinfo" onClick={clickEvent}>회원정보</Button>
      <Button name="orderlist" onClick={clickEvent}>주문내역</Button>
      <Button name="mypost" onClick={clickEvent}>쓴 글보기</Button>
      <Button name="deleteaccount" onClick={modalOpen}>회원탈퇴</Button>
    </SideBar>
    {<ShowComponent />}

    <Modal show={show} onHide={modalClose} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>정말로 탈퇴하겠습니까?</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        탈퇴시 개인정보와 함께 모든 이용내역이 지워집니다.<br />
            마지막으로 PASSWORD를 입력하시고 회원 탈퇴를 눌러주세요.
        <input type="password" onChange={e => setPassword(e.target.value)} />
      </Modal.Body>
      <Modal.Footer>
        <Button variant="danger" onClick={deleteAccout}>
          회원탈퇴
        </Button>
        <Button variant="secondary" onClick={modalClose}>
          취소
        </Button>
      </Modal.Footer>
    </Modal>
  </Container>
}
export default MyPage;

const Container = styled.div`

`
const SideBar = styled.div`

`
const MenuButton = styled(Button)`
  display:block;
  margin: 10px;
`
