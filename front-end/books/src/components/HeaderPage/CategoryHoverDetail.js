import { useState } from "react"
import { Link } from "react-router-dom"

import { Paper, Button } from "@material-ui/core"
import styled, { keyframes } from "styled-components"
const ENV = process.env.NODE_ENV;

const tagMap = ["총류", "철학", "종교", "사회과학", "자연과학", "기술과학", "예술", "언어", "문학", "역사"]

const CategoryHoverDetail = ({genreData, show}) => {
  const [choiceGenre, setChoiceGenre] = useState(null);

  const onHoverEvent = (idx) => {
    setTimeout(() => setChoiceGenre(genreData[idx]), 200)
  }

  return <HoverComponent show={show ? 1 : 0} elevation={9}>
    <GenreTag>
      {genreData && tagMap.map((tagName, idx) => {
        return <GenreButton color="primary" onMouseEnter={() => onHoverEvent(idx)} key={idx}>
          {tagName}
        </GenreButton>
      })}
    </GenreTag>

    <ItemsContainer>
      {choiceGenre && choiceGenre.map((item, idx) => (
        <div key={idx}>
          <Link to={{pathname:`/detail/${item.id}`, state:{book:item}}}>
            {
              ENV === 'development' ?
                <StyledPaper component={ItemImage} src={item.imageUrl} elevation={2} /> :
                <StyledPaper component={ItemImage} src={`/image/${item.upload_image_name}`} elevation={2} />
            }
          </Link>
          <ItemTitle>{item.name}</ItemTitle>
        </div>       
      ))}
      </ItemsContainer>
  </HoverComponent>
}
export default CategoryHoverDetail;


const slideDown = keyframes`
	0% {
		transform: translateY(-100%);
	}
	50%{
		transform: translateY(8%);
	}
	65%{
		transform: translateY(-4%);
	}
	80%{
		transform: translateY(4%);
	}
	95%{
		transform: translateY(-2%);
	}			
	100% {
		transform: translateY(0%);
	}		
`

const HoverComponent = styled(Paper)`
  position: absolute;
  display: ${props => props.show ? "block" : "none"};

  top: -10px;
  left: 100px;

  background-color: #e8eaf6;
  border-radius: 10px;
  width:90%;

  animation-duration: 1.5s;
  animation-timing-function: ease-out;
  animation-name: ${slideDown};
`
const GenreTag = styled.div`
  float:left;

  width:10%;
  display: flex;
  flex-direction: column;
  
  padding: 0;
`

const GenreButton = styled(Button)`
  border:0 none;
  background-color: transparent;
  /* height:10%; */
  
  font-weight: bolder;

  &:hover {
    background-color:#9fa8da
  }
`
const ItemsContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
`
const StyledPaper = styled(Paper)`
  
`
const ItemImage = styled.img`
  margin: 20px;
  width:160px;
  height: 240px;
  object-fit: cover;
`
const ItemTitle = styled.div`
  margin: 5px 0;
  font-size: 20px;
  font-family: 'Sunflower', sans-serif;
`