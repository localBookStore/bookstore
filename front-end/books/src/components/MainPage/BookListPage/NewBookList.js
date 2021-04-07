import { useState, useEffect } from "react"
import { NavLink } from "react-router-dom"
import axios from "axios";

import { Image } from "react-bootstrap";
import styled from "styled-components";

const genreMap = {
  0: '총류',
  1: '철학',
  2: '종료',
  3: '사회과학',
  4: '자연과학',
  5: '기술과학',
  6: '예술',
  7: '언어',
  8: '문학',
  9: '역사'
}

const NewBookList = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/index/newitems/`)
      .then(({data: { _embedded: { defaultList }}}) => setBooks(defaultList))
      .catch(err => console.log(err))
  }, [])
  
  console.log(books)
  return <Container>
    {books.length && books.map((book, idx) => {
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
};
export default NewBookList;

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