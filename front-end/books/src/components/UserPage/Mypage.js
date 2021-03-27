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
    switch(selected) {
      case "deleteaccount":
        return console.log(selected)
        break
      default:
        console.log("선택 안되었음")
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
