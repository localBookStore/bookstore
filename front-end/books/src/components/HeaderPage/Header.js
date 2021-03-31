import { useState, useEffect } from "react"
import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"
import styled from "styled-components"

const Header = () => {
  const [visible, setVisible] = useState(true);
  const { location: { pathname } } = window;

  return <HeaderContainer visible={visible}>
    <SearchBar />
    <CategoryBar />
  </HeaderContainer>
}
export default Header;

const HeaderContainer = styled.div`
  display: ${props => props.visible ? "" : "none"};
  position: relative;
  width: 100%;
  height: auto;
`