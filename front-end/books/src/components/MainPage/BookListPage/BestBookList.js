import { NavLink } from "react-router-dom"
import { genreMap } from 'feature/GenreMap'

import { Paper } from "@material-ui/core"
import { Image, Table } from "react-bootstrap";
import styled from "styled-components";
const ENV = process.env.NODE_ENV;

const BestBookList = ({ location:{ state } }) => {
  const bestBooks = state;

  return <>
    {!bestBooks.length ? <div>베스트 도서가 없습니다.</div> : 
    <>
    <ContainerTitle> 👍 상위 30개의 BEST 도서입니다</ContainerTitle>
    <Container responsive="sm">
    <thead>
      <tr>{["포스터이미지", "제목", "장르", "출판사", "가격"].map((tag, idx) => (
        <th key={idx}><ItemContent>{tag}</ItemContent></th>
      ))}
      </tr>
      </thead>
      <tbody>
        {bestBooks.map((book, idx) => (
          <tr key={idx}>
            <td><NavButton to={{pathname:`/detail/${book.id}`, state:{book}}}>
            {
              ENV === 'development' ?
              <StyledPaper component={Image} src={book.imageUrl} elevation={8} /> :
              <StyledPaper component={Image} src={`/image/${book.uploadImageName}`} elevation={8} />
            }
            </NavButton></td>
            <td><ItemContent>{book.name}</ItemContent></td>
            <td><ItemContent>{genreMap[book.category_id]}</ItemContent></td>
            <td><ItemContent>{book.publisher}</ItemContent></td>
            <td><ItemContent>{book.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}</ItemContent></td>
          </tr>
        ))}
      </tbody>
    </Container>
    </>
    }
    </>
};
export default BestBookList;

const ContainerTitle = styled.div`
  margin: 30px 10%;
  font-size: 28px;
  font-weight: bold;
`

const Container = styled(Table)`
  margin: 0 auto;
  width: 80%;

  td {
    vertical-align: middle; 
    font-weight: 600;
  } 
`

const StyledPaper = styled(Paper)`
  width: 180px;
  height: 220px;
  object-fit: cover;
`
const NavButton = styled(NavLink)`
  margin: 0 10% 0 0;
  float: right;
`

const ItemContent = styled.div`
  font-size: 20px;
  text-align: center;

  margin: auto 0;
`