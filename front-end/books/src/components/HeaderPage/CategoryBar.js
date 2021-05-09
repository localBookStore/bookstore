import { useState } from "react";
import { NavLink } from "react-router-dom";

import CategoryHoverDetail from "./CategoryHoverDetail";

import { Button } from "@material-ui/core"
import styled from "styled-components";

// JSX duplicated code 
const NavigationButton = ({ title, url, data=null }) => {
	
	return <NavButton
		variant="contained"
		component={NavLink} 
		activeClassName={"selected"}
		to={{pathname:url, state:data}}
		>{title}</NavButton>
}

const CategoryBar = ({genreData, bestBooks, newBooks}) => {	
	const [isHover, setIsHover] = useState(false);

	const hoverOn = () => {
		setTimeout(() => setIsHover(true), 100)
	}
	const hoverOff = () => {
		setTimeout(() => setIsHover(false), 100)
	}

	return <AllContainer>
      <GenreContainer onMouseLeave={hoverOff}>
				<GenreButton variant="contained" onMouseEnter={hoverOn}>장르별</GenreButton>
				{genreData.length ? 
						<CategoryHoverDetail genreData={genreData} hoverOff={hoverOff} show={isHover} /> : null 
				}
			</GenreContainer>
			<NavigationButton url="/bestbooklist" data={bestBooks} title="베스트"/>
			<NavigationButton url="/newbooklist" data={newBooks} title="최신작"/>
			<NavigationButton url="/community" title="커뮤니티"/>

		</AllContainer>
};
export default CategoryBar;


const AllContainer = styled.div`
	position: relative;
	display: flex;
	justify-content: space-evenly;
	text-align: center;
	z-index: 3;

	margin: 30px 0 50px 0;
	padding: 0;

	width: 100%;
	height: auto;
	
`;
const GenreContainer = styled.div`

`
const GenreButton = styled(Button)`
	font-family: 'Nanum Gothic', sans-serif;
	font-weight: bold;
	font-size: 20px;
	background-color: #ede7f6;

	&:hover {
		color: #2F6D91;
	}
`
const NavButton = styled(Button)`
	font-family: 'Nanum Gothic', sans-serif;
	font-weight: bold;

	color: black;
	font-size: 20px;
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
