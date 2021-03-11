// import qs from "qs";

const BookList = ({location}) => {
  const {input, tag, books} = location.state
  console.log(location)
  return <>
    <div>태그명 : {tag} </div>
    <div>검색어 : {input}</div>
    {books ?
      books.map((book, idx) => {
        return <div key={idx}>
          <h2>{book.name}</h2>
          <p>{book.description}</p>
        </div>
      })
      :
      <div>검색 결과가 없습니다.</div>
    }
  </>
}
export default BookList;