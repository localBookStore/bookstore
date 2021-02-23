import logo from "../../icons/bookshop.svg"
import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"
import "./Header.css"

const Header = () => {
  return <div className="header">
    <button className="logo-button">
      <img src={logo} className="logo" alt="logo" />
    </button>
    <SearchBar />
    <CategoryBar />
  </div>
}
export default Header;