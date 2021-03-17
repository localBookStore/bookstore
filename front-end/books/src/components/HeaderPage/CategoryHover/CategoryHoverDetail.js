import styled, { keyframes } from "styled-components"
import { useState, useEffect } from "react"
import axios from "axios"

const tagMap = ["총류", "철학", "종교", "사회과학", "자연과학", 
                  "기술과학", "예술", "언어", "문학", "역사"]

const CategoryHoverDetail = ({}) => {
  const [genreData, setGenreData] = useState(null);

  useEffect(() => {
    const getGenreBooks = async () => {
      await axios.get("http://localhost:8080/api/genre/")
        .then(res => setGenreData(res.data))
        .catch(err => console.log(err.response))
    }
    getGenreBooks()
  }, [])

  return <HoverComponent>
    {genreData && console.log(genreData)}
    {genreData && tagMap.map((tagName, idx) => {
      return <button key={idx}>
        {tagName}
      </button>
    })}
    {/* {genreData && <EachGenreBooks />} */}
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
  top: 60px;
  left: 40px;

  background-color: rgba(235, 235, 235, 0.95);
  border-radius: 10px;
  width:90.5vw;
  height: 300px;

  animation-duration: 0.3s;
  animation-timing-function: ease-out;
  animation-name: ${slideUp};
`
