import { useHistory } from "react-router-dom";

import { Select, MenuItem , Button, TextField} from '@material-ui/core';
import styled from "styled-components";

const SearchBar = ({query, setQuery, searchEvent}) => {
  const history = useHistory();
  
  const initialize = () => {
    setQuery({
      ...query,
      input: "",
      tag: "name",
    })
  }

  const queryChangeEvent = (e) => {
    const { name, value } = e.target
    setQuery({
      ...query,
      [name]: value
    })
  }

  const enterEvent = (event) => {
    if (event.key === "Enter") clickEvent();
  };

  const clickEvent = () => {
    if (query.input !== "") {
      searchEvent().then(res => {
        const books = res.data._embedded.defaultList
        history.push({
          pathname: "/booklist",
          state: { books, input:query.input },
        });
        
      })
      .catch(() => alert("검색 결과가 없습니다.👋"));
    }
    else alert(" 📝 검색어를 입력하세요.")
    initialize();
  };

  return <EntireBar>
			<SelectTag 
        name="tag" 
        value={query.tag} 
        variant="outlined" 
        onChange={queryChangeEvent}
        >
				<MenuItem value="name">제목</MenuItem>
				<MenuItem value="author">저자</MenuItem>
			</SelectTag>

			<SearchInput 
        variant="outlined"
        label="Search"
        placeholder="Search..." 
        size="medium"
        name="input"
        value={query.input}
        onKeyPress={enterEvent}
        onChange={queryChangeEvent}
      />
			<SearchButton color="primary" onClick={clickEvent}>🔍</SearchButton>
		</EntireBar>
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
  margin-left: 10px;

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
  & .MuiOutlinedInput-root {
    &.Mui-focused fieldset {
      border: 3px solid #5c6bc0;
    }
  }
`;
