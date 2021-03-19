import styled, { keyframes } from "styled-components"
import { useState, useEffect } from "react"
import axios from "axios"

const tagMap = ["총류", "철학", "종교", "사회과학", "자연과학",
  "기술과학", "예술", "언어", "문학", "역사"]

const CategoryHoverDetail = ({ }) => {
  const [genreData, setGenreData] = useState(null);
  const [choiceGenre, setChoiceGenre] = useState(null);

  useEffect(() => {
    const getGenreBooks = async () => {
      await axios.get("http://localhost:8080/api/index/genre/")
        .then(res => setGenreData(res.data))
        .catch(err => console.log(err.response))
    }
    getGenreBooks()
  }, [])

  const onHoverEvent = (idx) => {
    setChoiceGenre(genreData[idx])
  }

  return <HoverComponent>
    <GenreTag>
      {genreData && tagMap.map((tagName, idx) => {
        return <GenreButton
          bakgroundColor="blue"
          key={idx}
          onMouseEnter={() => onHoverEvent(idx)}
        >
          {tagName}
        </GenreButton>
      })}
    </GenreTag>
    <Items>
      {choiceGenre && choiceGenre.map((res, idx) => {
        return <div key={idx}>
          <EachItemButton><ItemImage src={res.imageUrl} alt={idx} /></EachItemButton>
          <ItemTitle>{res.name}</ItemTitle>
        </div>
      })}
    </Items>
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
  display: flex;
  top: 60px;

  left: 2%;
  margin: auto;

  background-color: rgba(235, 235, 235, 0.95);
  border-radius: 10px;
  width:95%;
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


const GenreButton = styled.button`
  border:0 none;
  background-color: transparent;
  height:10%;
  
  font-weight: bolder;

  &:hover {
    background-color: ${props => props.bakgroundColor && "#B3DFFF"}
  }
`

const Items = styled.div`
  width:90%;
  float: right;
  display: grid;
  grid-template-columns: repeat(3, 1fr);

  margin: 50px;
  
`

const EachItemButton = styled.button`
  border: 0 none;
  width: auto;
  height: 200px;
  overflow: hidden;

`
const ItemImage = styled.img`
  max-height: 100%;
  height: auto;
  display: block;
`

const ItemTitle = styled.div`

`
