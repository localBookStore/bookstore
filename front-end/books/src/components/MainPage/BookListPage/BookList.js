// import qs from "qs";

const BookList = ({location}) => {

  console.log(location)
  return <>
    <div>검색어 : {location.state.inputs}</div>
  </>
}
export default BookList;
