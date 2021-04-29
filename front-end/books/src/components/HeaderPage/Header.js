import { useState, useEffect } from "react"
import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"
import styled from "styled-components"

const Header = () => {
  
  return <HeaderContainer>
    <SearchBar />
    <CategoryBar />
  </HeaderContainer>
}
export default Header;

const HeaderContainer = styled.div`
  
  position: relative;
  width: 100%;
  height: auto;
`