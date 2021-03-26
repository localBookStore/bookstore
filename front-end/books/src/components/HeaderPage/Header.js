import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"
import styled from "styled-components"
const Header = () => {
  if (window.location.pathname === '/login') return null;
  if (window.location.pathname === '/signup') return null;

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