import "./SearchBar.css"
import { useState } from "react"
import { useHistory, useLocation } from "react-router-dom"

const SearchBar = () => {
  const [inputs, setInputs] = useState(null);
  const history = useHistory();
  const location = useLocation();

  const enterEvent = (event) => {
    console.log(event)
    if (event.key === 'Enter') {
      setInputs(event.target.value)
      console.log(inputs)
      history.push('/booklist')
    }
  }

  const clickEvent = () => {
    // console.log(inputs)
    history.push("/booklist")
  }

  return <>
    <div className="search-items">
      <input
        className="search-bar"
        placeholder="Search..."
        onKeyPress={enterEvent}
      />
      <button
        className="search-button"
        onClick={clickEvent}
      >검색
        </button>
    </div>
    {/* {console.log(inputs)} */}
  </>
}
export default SearchBar