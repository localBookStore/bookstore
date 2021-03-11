import styled, { keyframes } from "styled-components"
import { useState, useEffect } from "react"
import axios from "axios"


const CategoryHoverDetail = () => {
  const [genreData, setGenreData] = useState([])

  useEffect(() => {
    const getGenreBooks = async () => {
      await axios.get("http://localhost:8080/api/genre/", {
        body:{
        category_id: 2
      }
    })
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err.response))
    }
    getGenreBooks()
  }, [])

  return <HoverComponent>

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

