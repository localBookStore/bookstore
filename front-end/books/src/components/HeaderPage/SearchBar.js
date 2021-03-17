import { useState } from "react"
import { useHistory } from "react-router-dom"
import axios from "axios"

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearchPlus } from '@fortawesome/free-solid-svg-icons'
import styled from "styled-components"

const SearchBar = () => {
  const [input, setInput] = useState("");
  const [tag, setTag] = useState("name");
  const history = useHistory();

  const routing = (items) => {
    history.push({
      pathname: "/booklist",
      state: { items }
    })
  }

  const getBooklist = async () => {

    await axios.get("http://localhost:8080/api/items/", { params: { input, tag } })
      .then(res => {
        const books = res.data._embedded.itemDtoList
        routing(books)
      })
      .catch(() => {
        routing(null)
      })
    setInput("")
  }

  const enterEvent = (event) => {
    if (event.key === 'Enter') {
      clickEvent();
    }
  }
  const clickEvent = () => {
    if (input) {
      getBooklist()
    }
  }

  return <EntireBar>
    <SelectTag value={tag} onChange={event => setTag(event.target.value)}>
      <option value="name">제목</option>
      <option value="author">저자</option>
    </SelectTag>
    <SearchInput
      value={input}
      placeholder="Search..."
      onChange={(event) => { setInput(event.target.value) }}
      onKeyPress={enterEvent}
    />
    <SearchButton onClick={clickEvent}>
      <FontAwesomeIcon icon={faSearchPlus} style={{ fontSize: "33px", color: "#000" }} />
    </SearchButton>
  </EntireBar>
}
export default SearchBar


const EntireBar = styled.div`
  position: relative;
  width: 850px;
  top: 80px;
  left: 280px;
`
const SearchInput = styled.input`
  position: relative;
  left: 125px;

  border: 1.5px solid;
  background-color: whitesmoke;
  border-radius: 5px 5px 5px 5px;
  width: 680px;
  height: 70px;
  
  font-weight: 500;
  
  padding-left: 20px;
`
const SearchButton = styled.button`
  position: absolute;
  background-color: #E8D6A5;
  top: 10px;
  left: 680px;
  width: 110px;
  height: 50px;
  margin: 0 10px;
  font-size: 18px;
  font-weight: 700;
  border-radius: 15px 15px 15px 15px;
`
const SelectTag = styled.select`
  position: absolute;
  left: 0px;
  top: 10px;
  width: 110px;
  height: 50px;
  margin: 0 10px;
  font-size: 18px;
  font-weight: 700;
  z-index: 1;
`