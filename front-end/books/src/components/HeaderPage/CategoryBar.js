import CategoryHoverDetail from "./CategoryHoverDetail";
import { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import axios from "axios"

import { Button } from "@material-ui/core"
import styled from "styled-components";
import { SettingsBluetooth } from "@material-ui/icons";

const CategoryBar = () => {
	const [isHover, setIsHover] = useState(false);
	const [genreData, setGenreData] = useState([]);

	const hoverOn = () => {
		setTimeout(() => {
			return setIsHover(true)
		}, 100)
	}
	const hoverOff = () => {
		setTimeout(() => {
			return setIsHover(false)
		}, 100)
	}

	useEffect(() => {
		getGenreBooks()
	}, []);

	const getGenreBooks = async () => {
		const { data } = await axios.get("http://localhost:8080/api/index/genre/");
		setGenreData(data);
	};

	return <AllContainer>
      <GenreContainer onMouseLeave={hoverOff}>
				<GenreButton onMouseEnter={hoverOn}>장르별</GenreButton>
				<CategoryHoverDetail genreData={genreData} hoverOff={hoverOff} show={isHover} />
			</GenreContainer>
			
			<NavButton component={NavLink} activeClassName={"selected"} to="/bestbooklist">베스트</NavButton>
			<NavButton component={NavLink} activeClassName={"selected"} to="/newbooklist">최신작</NavButton>
			<NavButton component={NavLink} activeClassName={"selected"} to="/community">커뮤니티</NavButton>

		</AllContainer>
};
export default CategoryBar;

const AllContainer = styled.div`
	position: relative;
	display: flex;
	justify-content: space-evenly;
	text-align: center;
	z-index: 1;

	margin: 30px 0 30px 0;
	padding: 0;

	width: 100%;
	height: auto;
	
`;
const GenreContainer = styled.div`

`
const GenreButton = styled(Button)`
font-size: 22px;
	font-weight: 700;
	
	&:hover {
		color: #2F6D91;
		font-weight: 700;
	}
`
const NavButton = styled(Button)`
	color: black;
	font-size: 22px;
	font-weight: 700;
	z-index: -1;
	text-decoration: none;

	&.selected {
		color: #2F6D91;
		font-weight: 700;
	}
`;
