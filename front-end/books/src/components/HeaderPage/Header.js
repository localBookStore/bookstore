import logo from "../../icons/books.jpg"
import SearchBar from "./SearchBar"
import "./Header.css"

const Header = () => {
  return <>
    <img src={logo} className="logo" alt="logo" />
    <SearchBar />
  </>
}
export default Header;