import { useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";

import { Select, MenuItem , Button, TextField} from '@material-ui/core';
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
        variant="outlined"
        label="Search"
        placeholder="Search..." 
        size="medium"
        onKeyPress={enterEvent}
        onChange={(e) => setInput(e.target.value)} 
      />
			<SearchButton color="primary" onClick={clickEvent}>🔍</SearchButton>
		</EntireBar>
	);
};
export default SearchBar;

const EntireBar = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 40px;

`
const SearchInput = styled(TextField)`
  width: 55vw;
  margin-left: 30px;

  & label.Mui-focused {
    color: #1e88e5;
  }
  & .MuiOutlinedInput-root {
    &.Mui-focused fieldset {
      border: 3px solid #5c6bc0;
    }
  }
`
const SearchButton = styled(Button)`
  font-size: 27px;
`;
const SelectTag = styled(Select)`
  width: 5vw;
`;
