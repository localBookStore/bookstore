// import qs from "qs";
import {useState, useEffect} from "react"
import axios from "axios"

const BookList = ({location, match, history}) => {
  const [books, setBooks] = useState(false);
  const {inputs, tag} = location.state;

  useEffect(() => {
    const getBooklist = async () => {
      await axios.get("http://localhost:8080/api/items/", {
        params: {
          name:inputs,
        }
      })
        .then(res => {
          console.log(res.data._embedded.itemDtoList)
          setBooks(res.data._embedded.itemDtoList)
        })
        .catch(err => {
          console.log(err.response)
          setBooks(false)
        })
    }
    getBooklist()
  }, [inputs])

  return <>
    <div>태그명 : {tag} </div>
    <div>검색어 : {inputs}</div>
    {!books ? 
    <div>검색 결과가 없습니다.</div> 
    :
    books.map((book, idx) => {
      return <div key={idx}>
        <h2>{book.name}</h2>
        <p>{book.description}</p>
      </div>
    })}
  </>
}
export default BookList;