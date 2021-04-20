import { useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";

import { Select, Input , MenuItem , Button} from '@material-ui/core';
import styled from "styled-components";

const SearchBar = () => {
  const [input, setInput] = useState("");
  const [tag, setTag] = useState("name");
  const history = useHistory();

  const getBookList = () => {
    axios.get("api/items/", {params:{ input, tag }})
      .then(res => {
        const books = res.data._embedded.defaultList
        history.push({
          pathname: "/booklist",
          state: { books },
        });
      })
      .catch(() => alert("검색 결과가 없습니다."));
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
				<MenuItem value="name">제목</MenuItem>
				<MenuItem value="author">저자</MenuItem>
			</SelectTag>
			<SearchInput 
      style={{ fontSize: "23px"}}
      variant="outlined"
      label="Search"
      placeholder="Search..." 
      
      onKeyPress={enterEvent}
      onChange={(e) => setInput(e.target.value)} 
      />
			<SearchButton variant="outlined" color="primary" onClick={clickEvent}>🔍</SearchButton>
		</EntireBar>
	);
};
export default SearchBar;

const EntireBar = styled.div`
  position: relative;
  left: 4%;
  width: 100%;
  height: auto;
`
const SearchInput = styled(Input)`
  position: relative;
  left: 12%;

  width: 60%;
  height: 60px;
  
  padding: 0 20px;
  margin: 40px 0;
`
const SearchButton = styled(Button)`
  position: relative;
  left: 5%;

  border-radius: 20px 20px 20px 20px;
  width: 6%;

  font-size: 20px;
  text-align: center;
`;
const SelectTag = styled(Select)`
  position: relative;
  left: 10%;

  width: 8%;

  font-weight: 700;
`;
