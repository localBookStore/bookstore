import { useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";

import { BiSearchAlt } from "react-icons/bi"
import styled from "styled-components";

const SearchBar = () => {
  const [input, setInput] = useState("");
  const [tag, setTag] = useState("name");
  const history = useHistory();

  const getBookList = () => {
    axios.get("http://localhost:8080/api/items/", {params:{ input, tag }})
      .then(res => {
        const books = res.data._embedded.defaultList
        history.push({
          pathname: "/booklist",
          state: { books },
        });
      })
      .catch(err => alert("검색 결과가 없습니다."));
    setInput("");
  };

  const enterEvent = (event) => {
    if (event.key === "Enter") {
      clickEvent();
    }
  };
  const clickEvent = () => {
    if (input) {
      getBookList();
    }
  };

  return (
		<EntireBar>
			<SelectTag value={tag} onChange={(e) => setTag(e.target.value)}>
				<option value="name">제목</option>
				<option value="author">저자</option>
			</SelectTag>
			<SearchInput value={input} placeholder="Search..." onChange={(e) => setInput(e.target.value)} onKeyPress={enterEvent} />
			<SearchButton onClick={clickEvent}>
				<SearchIcon/>
			</SearchButton>
		</EntireBar>
	);
};
export default SearchBar;

const EntireBar = styled.div`
  position: relative;
  width: 100%;
  height: auto;
`;
const SearchInput = styled.input`
  position: relative;
  left: 12%;

  border: 1.5px solid;
  background-color: whitesmoke;
  border-radius: 5px 5px 5px 5px;
  width: 60%;
  height: 7vh;

  font-weight: 500;

  padding-left: 20px;
`;
const SearchButton = styled.button`
  position: relative;
  left: 5%;

  border-radius: 15px 15px 15px 15px;
  background-color: #e8d6a5;
  width: 6%;
  height: 5vh;

`;
const SelectTag = styled.select`
  position: relative;
  left: 10%;
  margin: 40px 0;

  width: 8%;
  height: 5vh;

  font-size: 18px;
  font-weight: 700;
`;
const SearchIcon = styled(BiSearchAlt)`
  font-size: 120%;
`