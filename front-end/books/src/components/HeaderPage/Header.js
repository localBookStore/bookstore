import axios from "axios";
import { useState, useEffect } from "react";

import SearchBar from "./SearchBar"
import CategoryBar from "./CategoryBar"

import styled from "styled-components"

const Header = () => {
	const [genreData, setGenreData] = useState([]);

  useEffect(() => {
		getGenreBooks()
	}, []);

	const getGenreBooks = async () => {
		const { data } = await axios.get("api/index/genre/")
		setGenreData(data)
	};

  return <HeaderContainer>
    <SearchBar />
    <CategoryBar genreData={genreData}/>
  </HeaderContainer>
}
export default Header;

const HeaderContainer = styled.div`
  
  position: relative;
  width: 100%;
  height: auto;
`