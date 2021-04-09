import { useState, useEffect } from "react"
import { NavLink } from "react-router-dom"
import { genreMap } from "feature/GenreMap"

import { Image } from "react-bootstrap";
import styled from "styled-components"

const BookList = ({location}) => {
  
  const { books } = location.state

  return <Container>
  { books.map((book, idx) => {
    return <ItemContainer key={idx}>
      <ItemContent>
        <NavButton to={{pathname:`/detail/${book.id}`, state:{book}}}>
          <ItemImage src={book.imageUrl} rounded fluid/>
        </NavButton>
      </ItemContent>
      <ItemContent>
        제목: {book.name} <br />
        장르: {genreMap[book.category_id]} <br />
        가격: {book.price}
      </ItemContent>
      
    </ItemContainer> 
  })}
</Container>
}
export default BookList;

const Container = styled.div`
`
const ItemContainer = styled.div`
  margin: 20px 0;
  display: flex;
  justify-content: center;
  align-items: center;
`
const ItemImage = styled(Image)`
  width: 240px;
  height: 330px;
  object-fit: cover;
`
const NavButton = styled(NavLink)`
  margin: 0 10% 0 0;
  float: right;
`

const ItemContent = styled.div`
  margin: 0 4%;
  font-size: 22px;
  font-weight: 600;
  flex-grow: 1;
`