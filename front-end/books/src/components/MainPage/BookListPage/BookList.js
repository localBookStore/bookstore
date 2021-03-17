import styled from "styled-components"

const BookList = ({ history }) => {
  const { input, tag, items } = history.location.state

  const clickEvent = (book) => {
    history.push({
      pathname: "/detail",
      search: `?id=${book.id}`,
      state: book
    })
  }
  
  return <>
    {(input && tag) && <div>태그명 : {tag} </div>}
    {(input && tag) && <div>검색어 : {input}</div>}
    {items ?
      items.map((book, idx) => {
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