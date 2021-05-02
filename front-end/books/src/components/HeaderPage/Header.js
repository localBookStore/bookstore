import axios from "axios";
import { useState, useEffect } from "react";

import SearchBar from "./SearchBar";
import CategoryBar from "./CategoryBar";

import styled from "styled-components";

const Header = () => {
	const [genreData, setGenreData] = useState([]);
  const [bestBooks, setBestBooks] = useState([]);
  const [newBooks, setNewBooks] = useState([]);
  const [query, setQuery] = useState({
    input: "",
    tag: "name"
  });

  useEffect(() => {
		getGenreBooks();
    getBookLists(`api/index/bestitems/`, setBestBooks);
    getBookLists(`api/index/newitems/`, setNewBooks);
	}, []);

  // 10개의 장르별 3개의 책들
	const getGenreBooks = async () => {
		const { data } = await axios.get("api/index/genre/")
		setGenreData(data)
	};
  // 총 30개의 책(리스트)
  const getBookLists = async (url, setState) => {
    const { data } = await axios.get(url)
    setState(data._embedded.defaultList)
  }
  // 검색 요청
  const getSearchBookList = () => {
    return axios.get("api/items/", { params:{...query}})
  };

  return <HeaderContainer>
    <SearchBar 
      query={query}
      setQuery={setQuery}
      searchEvent={getSearchBookList} 
      />
    <CategoryBar 
      genreData={genreData}
      bestBooks={bestBooks}
      newBooks={newBooks}
      />
  </HeaderContainer>
}
export default Header;

const HeaderContainer = styled.div`
  position: relative;
  width: 100%;
  height: auto;
`