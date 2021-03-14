import styled from "styled-components"
import { useHistory } from "react-router-dom"

const BookList = ({ location }) => {
  const { input, tag, books } = location.state
  const history = useHistory();

  const clickEvent = (book) => {
    history.push({
      pathname:"/detail",
      search:`?id=${book.id}`,
      state: book
    })
  }


  return <>
    <div>태그명 : {tag} </div>
    <div>검색어 : {input}</div>
    {books ?
      books.map((book, idx) => {
        return <div key={idx}>
          <ImageButton onClick={() => clickEvent(book)}>
            <BookPosterImage src={book.imageUrl} alt="idx" />
          </ImageButton>
          <h2>{book.name}</h2>
          <p>{book.description}</p>
        </div>
      })
      :
      <h2>{input}에 대한 검색 결과가 없습니다.</h2>
    }
  </>
}
export default BookList;


const ImageButton = styled.button`
  border: 0 none;
  background-color: transparent;
`

const BookPosterImage = styled.img`
  width:240px;
  height:320px;
  object-fit: cover;
`