import axios from "axios"
import { useState, useEffect } from "react"

import { Button } from "react-bootstrap"
import styled from "styled-components"

const MyPage = ({ location }) => {
  const { state: { token } } = location;
  const [selected, setSelected] = useState("");
  const sideMenu = [
    { title: "회원정보", name: "userinfo" },
    { title: "주문내역", name: "orderlist" },
    { title: "쓴 글보기", name: "mypost" },
    { title: "회원탈퇴", name: "deleteaccount" },
  ]

  useEffect(() => {
    if (selected === 'userInfo') {

    } else if (selected === 'orderlist') {

    } else if (selected === 'mypost') {

    } else if (selected === 'deleteaccount') {
      
    }
  }, [selected])


  return <Container>
    {sideMenu.map((menu, idx) => {
      return <MenuButton key={idx} onClick={() => setSelected(menu.name)}>
        {menu.title}
      </MenuButton>
    })}
  </Container>
}
export default MyPage;

const Container = styled.div`

`
const SideBar = styled(Button)`

`
const MenuButton = styled(Button)`
  display:block;
  margin: 10px;
`
