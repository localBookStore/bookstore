import CategoryHoverDetail from "./CategoryHover/CategoryHoverDetail";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import styled from "styled-components";

const CategoryBar = () => {
  const [isHover, setIsHover] = useState(false);
  const [HoverIdx, setHoverIdx] = useState(null);
  const history = useHistory()

  const itemNames = ["장르별", "베스트", "최신작"]

  const ShowOnHover = () => {
    setIsHover(true)
  }

  const ShowOffHover = () => {
    setIsHover(false)
    setHoverIdx(null)
  }

  const ShowHoverDetail = (page) => {
    setHoverIdx(page)
  }

  return <AllContainer
    onMouseEnter={ShowOnHover}
    onMouseLeave={ShowOffHover}
  >
    {itemNames.map((itemName, idx) => {
      return <ItemButton
        key={idx}
        onMouseEnter={() => ShowHoverDetail(idx)}
      >
        {itemName}
      </ItemButton>
    })}
    <ItemButton>카테고리</ItemButton>
    {isHover && <CategoryHoverDetail page={HoverIdx} />}
  </AllContainer>
}
export default CategoryBar;


const AllContainer = styled.div`
  position: relative;
  text-align: center;
  height: 200px;
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
  
  transition:600ms ease all;
  
  &:hover {
    background:#AB4386;
    color:#fff
  }
`