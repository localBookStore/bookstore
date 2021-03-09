import { useState } from "react"
import { useHistory, useEffect } from "react-router-dom"
import styled from "styled-components"

const SearchBar = () => {
  const [inputs, setInputs] = useState("");
  const [tag, setTag] = useState("title");
  const history = useHistory();

  const enterEvent = (event) => {
    if (event.key === 'Enter' && inputs) {
      clickEvent();
    }
  }

  const clickEvent = () => {
    history.push({
      pathname:"/booklist",
      search:`?${tag}=${inputs}`,
      state:{
        inputs:inputs,
        tag:tag,
      }
    })
    return setInputs("")
  }

  return <EntireBar>
    <SelectTag value={tag} onChange={event => setTag(event.target.value)}>
      <option value="title">제목</option>
      <option value="author">저자</option>
    </SelectTag>
    <SearchInput
      value={inputs}
      placeholder="Search..."
      onChange={(event) => {setInputs(event.target.value)}}
      onKeyPress={enterEvent}
    />
    <SearchButton
      onClick={clickEvent}
    >검색
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
  background-color: powderblue;
  top: 10px;
  left: 680px;
  width: 110px;
  height: 50px;
  margin: 0 10px;
  font-size: 18px;
  font-weight: 700;
  border-radius: 40px 40px 40px 40px;
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