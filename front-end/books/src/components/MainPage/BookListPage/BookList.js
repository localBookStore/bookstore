// import qs from "qs";

const BookList = ({location}) => {

  const {state:{inputs, tag}} = location

  return <>
    <div>태그명 : {tag} </div>
    <div>검색어 : {inputs}</div>
  </>
}
export default BookList;
