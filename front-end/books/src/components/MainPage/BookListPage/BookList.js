import styled from "styled-components"

const BookList = ({location}) => {
  const {input, tag, books} = location.state
  console.log(books)
  return <>
    <div>태그명 : {tag} </div>
    <div>검색어 : {input}</div>
    {books ?
      books.map((book, idx) => {
        return <div key={idx}>
          <BookPosterImage src={book.imageUrl} alt="idx"/>
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

const BookPosterImage = styled.img`
  width:240px;
  height:320px;
  object-fit: cover;
`