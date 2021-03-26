import CategoryHoverDetail from "./CategoryHoverDetail";
import { useState } from "react";
import { useHistory, NavLink } from "react-router-dom";
import axios from "axios"

import styled from "styled-components";

const CategoryBar = () => {
  const [isHover, setIsHover] = useState(false);
  const history = useHistory()

  const getBooks = async (url) => {
    await axios.get(`http://localhost:8080/api/index/${url}`)
      .then(res => {
        const items = res.data._embedded.itemDtoList
        history.push({
          pathname: "/booklist",
          state: { items }
        })
      })
      .catch(err => console.log(err.response))
  }

  const getArticle = (url) => {
    history.push("/community")
  }

  return <AllContainer
    onMouseLeave={() => setIsHover(false)}>
    <ItemButton
      onMouseEnter={() => setIsHover(true)}>장르별</ItemButton>
    <ItemButton onClick={() => getBooks("bestitems/")}>베스트</ItemButton>
    <ItemButton onClick={() => getBooks("newitems/")}>최신작</ItemButton>
    <ItemButton onClick={() => getArticle("board/")}>커뮤니티</ItemButton>
    <IsShow show={isHover}><CategoryHoverDetail /></IsShow>
  </AllContainer>
}
export default CategoryBar;

const IsShow = styled.div`
  display: ${props => props.show? "block": "none"};
  width:0px;
`

const AllContainer = styled.div`
  position: relative;
  display: flex;
  justify-content: space-between;
  text-align: center;
  
  margin: 30px 0 60px 0;
  padding: 0;

  width: 100%;
  height: auto;
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