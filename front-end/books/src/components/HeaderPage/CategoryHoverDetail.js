import { useState } from "react"
import { NavLink } from "react-router-dom"

import { Button } from "react-bootstrap"
import styled, { keyframes } from "styled-components"

const tagMap = ["총류", "철학", "종교", "사회과학", "자연과학", "기술과학", "예술", "언어", "문학", "역사"]

const CategoryHoverDetail = ({genreData}) => {
  const [choiceGenre, setChoiceGenre] = useState(null);

  const onHoverEvent = (idx) => {
    setChoiceGenre(genreData[idx])
  }

  return <HoverComponent>
    <GenreTag>
      {genreData && tagMap.map((tagName, idx) => {
        return <GenreButton variant="outline-primary" onMouseEnter={() => onHoverEvent(idx)} key={idx}>
          {tagName}
        </GenreButton>
      })}
    </GenreTag>

    <ItemsContainer>
      {choiceGenre && choiceGenre.map((item, idx) => {
        return <div key={idx}>
          <ItemNavButton to={`/detail/${item.id}`}>
            <ItemImage src={item.imageUrl} alt={idx} />
          </ItemNavButton>
          <ItemTitle>{item.name}</ItemTitle>
        </div> 
      })}
      </ItemsContainer>
  </HoverComponent>
}
export default CategoryHoverDetail;


const slideUp = keyframes`
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
`

const HoverComponent = styled.div`
  position: absolute;
  
  top: 30px;

  left: 100px;
  margin: 0;

  background-color: rgba(235, 235, 235, 0.95);
  border-radius: 10px;
  width:90%;
  height: 400px;

  animation-duration: 0.3s;
  animation-timing-function: ease-out;
  animation-name: ${slideUp};
`
const GenreTag = styled.div`
  float:left;

  width:10%;
  height:100%;
  display: flex;
  flex-direction: column;
  
  padding: 0;
`

const GenreButton = styled(Button)`
  border:0 none;
  background-color: transparent;
  height:10%;
  
  font-weight: bolder;

  &:hover {
    background-color:"#B3DFFF"
  }
`
const ItemsContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
`

const ItemNavButton = styled(NavLink)`

`
const ItemImage = styled.img`
  margin: 20px;
  width:200px;
  height: 310px;
  object-fit: cover;

`
const ItemTitle = styled.div`

`