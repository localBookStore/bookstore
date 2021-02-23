import logo from "../../icons/books.jpg"
import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"
import "./Header.css"

const Header = () => {
  return <div className="header">
      <button className="logo-button"><img src={logo} className="logo" alt="logo" /></button>
      <SearchBar />
    <div className="category"><CategoryBar /></div>
  </div>
}
export default Header;