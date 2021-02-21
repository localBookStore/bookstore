import "./SearchBar.css"
import {useState} from "react"


const SearchBar = () => {
  const [inputs, setInputs] = useState(null);

  const enterEvent = (event) => {
    
    const {value} = event.target
    setInputs(value)
    if (event.key === 'Enter'){
      clickEvent()
    }
  }

  const clickEvent = () => {
    console.log(inputs, "------", "get props & move detail-page")
  }

  return <>
    <input
      className="search-bar"
      placeholder="검색고자하는 책제목을 입력하세요"
      onKeyDown ={enterEvent}
    />
    <button onClick={clickEvent}>검색</button>
  </>
}
export default SearchBar