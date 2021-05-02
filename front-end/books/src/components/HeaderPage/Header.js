import axios from "axios";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";

import SearchBar from "./SearchBar";
import CategoryBar from "./CategoryBar";

import styled from "styled-components";

const Header = () => {
  const history = useHistory();
	const [genreData, setGenreData] = useState([]);
  const [query, setQuery] = useState({
    input: "",
    tag: "name"
  });

  useEffect(() => {
		getGenreBooks();
	}, []);

	const getGenreBooks = async () => {
		const { data } = await axios.get("api/index/genre/")
		setGenreData(data)
	};

  const getBookList = () => {
    axios.get("api/items/", { params:{...query}})
      .then(res => {
        const books = res.data._embedded.defaultList
        history.push({
          pathname: "/booklist",
          state: { books },
        });
      })
      .catch(() => alert("검색 결과가 없습니다."));
    setQuery({
      ...query,
      input:"",
    });
  };

  return <HeaderContainer>
    <SearchBar 
      query={query}
      setQuery={setQuery}
      searchEvent={getBookList} 
      />
    <CategoryBar genreData={genreData}/>
  </HeaderContainer>
}
export default Header;

const HeaderContainer = styled.div`
  
  position: relative;
  width: 100%;
  height: auto;
`