import CategoryHoverDetail from "./CategoryHoverDetail";
import { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import axios from "axios"

import { Button } from "@material-ui/core"
import styled from "styled-components";

const CategoryBar = () => {
	const [isHover, setIsHover] = useState(false);
	const [genreData, setGenreData] = useState([]);

	const hoverOn = () => {
		setTimeout(() => {
			setIsHover(true)
		}, 100)
	}
	const hoverOff = () => {
		setTimeout(() => {
			setIsHover(false)
		}, 100)
	}

	useEffect(() => {
		getGenreBooks()
	}, []);

	const getGenreBooks = async () => {
		const { data } = await axios.get("api/index/genre/");
		setGenreData(data);
	};

	return <AllContainer>
      <GenreContainer onMouseLeave={hoverOff}>
				<GenreButton variant="contained" onMouseEnter={hoverOn}>장르별</GenreButton>
				{genreData.length && <CategoryHoverDetail genreData={genreData} hoverOff={hoverOff} show={isHover} />}
			</GenreContainer>
			
			<NavButton
				variant="contained"
				component={NavLink} 
				activeClassName={"selected"} 
				to="/bestbooklist"
				>베스트</NavButton>
			<NavButton 
				variant="contained" 
				component={NavLink} 
				activeClassName={"selected"} 
				to="/newbooklist"
				>최신작</NavButton>
			<NavButton 
				variant="contained" 
				component={NavLink} 
				activeClassName={"selected"} 
				to="/community"
				>커뮤니티</NavButton>

		</AllContainer>
};
export default CategoryBar;

const AllContainer = styled.div`
	position: relative;
	display: flex;
	justify-content: space-evenly;
	text-align: center;
	z-index: 1;

	margin: 30px 0 50px 0;
	padding: 0;

	width: 100%;
	height: auto;
	
`;
const GenreContainer = styled.div`

`
const GenreButton = styled(Button)`
	font-family: 'Nanum Gothic', sans-serif;
	font-weight: 700;
	font-size: 24px;
	background-color: #ede7f6;

	&:hover {
		color: #2F6D91;
	}
`
const NavButton = styled(Button)`
	font-family: 'Nanum Gothic', sans-serif;
	font-weight: 700;

	color: black;
	font-size: 24px;
	background-color: #ede7f6;

	z-index: -1;
	text-decoration: none;

	&:hover {
		background-color: #c5cae9;
		
	}

	&.selected {
		background-color: #bbdefb;
	}
`;
