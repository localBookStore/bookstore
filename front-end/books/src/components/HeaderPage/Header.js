import logo from "../../icons/bookshop.svg"
import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"
import {useHistory} from "react-router-dom"
import "./Header.css"

const Header = () => {
  const history = useHistory()

  const goHome = () => {
    history.push('/')
  }

  return <div className="header">
    <button 
    className="logo-button"
    onClick={goHome}
    >
      <img src={logo} className="logo" alt="logo" />
    </button>
    <SearchBar />
    <CategoryBar />
  </div>
}
export default Header;