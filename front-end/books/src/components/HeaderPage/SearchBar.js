import "./SearchBar.css"
import { useState } from "react"


const SearchBar = () => {
  const [inputs, setInputs] = useState(null);

  const enterEvent = (event) => {

    const { value } = event.target
    setInputs(value)
    if (event.key === 'Enter') {
      clickEvent()
    }
  }

  const clickEvent = () => {
    console.log(inputs, "------", "get props & move detail-page")
  }

  return <>
    <div className="search-items">
      <input
        className="search-bar"
        placeholder="Search..."
        onKeyDown={enterEvent}
      />
      <button
        className="search-button"
        onClick={clickEvent}
        
        >검색
        </button>
    </div>
  </>
}
export default SearchBar