import CategoryHoverDetail from "./CategoryHoverDetail";
import { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import axios from "axios";

import styled from "styled-components";

const CategoryBar = () => {
	const [isHover, setIsHover] = useState(false);
	const [genreData, setGenreData] = useState([]);

	const hoverOn = () => setIsHover(true);
	const hoverOff = () => setIsHover(false);

	useEffect(() => {
		getGenreBooks();
	}, []);

	const getGenreBooks = async () => {
		const { data } = await axios.get("http://localhost:8080/api/index/genre/");
		setGenreData(data);
	};

	return <AllContainer>
      {genreData.length && <GenreContainer onMouseLeave={hoverOff}>
					<ItemButton onMouseEnter={hoverOn}>장르별</ItemButton>
					{isHover && <CategoryHoverDetail genreData={genreData} hoverOff={hoverOff} />}
				</GenreContainer>
			}
			<NavButton to="/bestbooklist">베스트</NavButton>
			<NavButton to="/newbooklist">최신작</NavButton>
			<NavButton to="/community">커뮤니티</NavButton>

		</AllContainer>
};
export default CategoryBar;

const AllContainer = styled.div`
	position: relative;
	display: flex;
	justify-content: space-evenly;
	text-align: center;

	margin: 30px 0 30px 0;
	padding: 0;

	width: 100%;
	height: auto;
	z-index: 1;
`;
const GenreContainer = styled.div`

`
const ItemButton = styled.button`
	background: #50a3c7;
	border: 0 none;
	border-radius: 5px 5px 5px 5px;

	color: #fff;
	font-size: 1.3em;
	font-weight: bolder;

	transition: all 600ms;

	&:hover {
		background: #ab4386;
		color: #fff;
	}
`;
const NavButton = styled(NavLink)``;
