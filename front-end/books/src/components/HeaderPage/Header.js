import logo from "../../icons/books.jpg"
import SearchBar from "./SearchBar"
import "./Header.css"

const Header = () => {
  return <div className="header">
      <img src={logo} className="logo" alt="logo" />
      <SearchBar />
  </div>
}
export default Header;