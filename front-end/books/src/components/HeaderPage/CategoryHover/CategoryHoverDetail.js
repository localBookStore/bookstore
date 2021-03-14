import styled, { keyframes } from "styled-components"
import { useState, useEffect } from "react"
import axios from "axios"

const tagMap = {0:"총류", 1:"철학", 2:"종교", 3:"사회과학", 4:"자연과학", 
                  5:"기술과학", 6:"예술", 7:"언어", 8:"문학", 9:"역사"}

const CategoryHoverDetail = () => {
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
    {console.log(genreData)}
    
    
  </HoverComponent>
}
export default CategoryHoverDetail;


const slideUp = keyframes`
  from {
    transform: translateY(-50px);
  }
  to {
    transform: translateY(10px);
  }
`

const HoverComponent = styled.div`
  position: absolute;
  top: 60px;
  left: 40px;

  background-color: rgba(224, 255, 239, 0.9);
  border-radius: 5px;
  width:90.5vw;
  height: 300px;

  animation-duration: 0.3s;
  animation-timing-function: ease-out;
  animation-name: ${slideUp};
`
