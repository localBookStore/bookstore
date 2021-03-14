import CategoryHoverDetail from "./CategoryHover/CategoryHoverDetail";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios"
import styled from "styled-components";

const CategoryBar = () => {
  const [isHover, setIsHover] = useState(false);
  // const [books, setBooks] = useState(null);
  const history = useHistory()

  const getBestBooks = async () => {
    await axios.get("http://localhost:8080/api/items/bestitems/")
      .then(res => {
        const books = res.data._embedded.itemDtoList
        history.push({
          pathname: "/booklist",
          state: { books }
        })
      })
      .catch(err => console.log(err.response))
  }

  return <AllContainer
    onMouseLeave={() => setIsHover(false)}>
    <ItemButton
      onMouseEnter={() => setIsHover(true)}
    >장르별</ItemButton>
    <ItemButton onClick={getBestBooks}>베스트</ItemButton>
    <ItemButton>최신작</ItemButton>
    <ItemButton>커뮤니티</ItemButton>
    {isHover && <CategoryHoverDetail />}
  </AllContainer>
}
export default CategoryBar;


const AllContainer = styled.div`
  position: relative;
  text-align: center;
  height: 70px;
  padding: 0;
  width: 100%;
  top: 150px;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  z-index: 1;
`
const ItemButton = styled.button`
  position:relative;
  padding:0 2em;

  background:#50A3C7;
  border:0 none;
  border-radius: 5px 5px 5px 5px;
  outline:none;
  width:260px;
  height:50px;
  margin: 0 auto;

  color:#fff;
  font-size:1.3em;
  font-weight: bolder;
  
  transition: all 600ms;
  
  &:hover {
    background:#AB4386;
    color:#fff
  }
`