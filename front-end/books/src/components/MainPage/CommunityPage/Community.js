import { useState, useEffect } from "react"
import { NavLink } from "react-router-dom"
import axios from "axios"

import { Table, Button } from "react-bootstrap"
import styled from "styled-components"

const Community = ({ history }) => {
  const [articles, setArticles] = useState([]);
  const section = ["글 번호", "분류", "제목", "작성시간", "댓글 수"]

  useEffect(() => {
    axios.get("http://localhost:8080/api/board")
      .then(res => setArticles(res.data.dtoList))
      .catch(err => console.log(err.response))
  }, [])

  const articleDetailEvent = (id) => {
    history.push(`/community/detail/${id}`)
  }

  console.log(articles)
  return <div>
    {articles.length ? <Container hover responsive >
        <thead>
          <tr>{section.map((res, idx) => {
            return <td key={idx}>{res}</td>
          })}</tr>
        </thead>

        <tbody>
          {articles.map((article, idx) => {
            return <tr key={idx} onClick={() => articleDetailEvent(article.id)}>  
              <td>{article.id}</td>
              <td>{article.category}</td>
              <td>{article.title}</td>
              <td>{article.modifiedDate}</td>
            </tr>
          })}
          </tbody>
      </Container>
      : <div>게시글이 없습니다.</div>}
    <NavLink to="/community/register">
      <Button variant="info">게시글 등록</Button>
    </NavLink>
  </div >
}
export default Community;


const Container = styled(Table)`
  margin: 0 auto;
  width: 80%;

  td {
    text-align: center;
    vertical-align: middle; 
    font-weight: 600;
    height: 100px;
  } 
`

const ItemImage = styled(Image)`
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