import { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import axios from "axios"

import { Table } from "react-bootstrap"
import { Button } from "@material-ui/core"
import styled from "styled-components"

const Community = ({ history }) => {
  const [articles, setArticles] = useState([]);
  const section = ["글 번호", "분류", "제목", "작성시간", "댓글 수"]

  useEffect(() => {
    getArticles();
  }, [])

  const getArticles = async () => {
    const {data: { dtoList }} = await axios.get("api/board");
    setArticles(dtoList)
  }

  const articleDetailEvent = (id) => {
    history.push(`/community/detail/${id}`)
  }

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
              <td>{article.replyCount}</td>
            </tr>
          })}
        </tbody>
      </Container>
      : 
      <NoArticle>🙅‍♂️ 게시글이 없습니다</NoArticle>
      }

    <div style={{display:"flex", justifyContent:"flex-end"}}>
      <PostButton
        component={Link}
        to="/community/register"
        variant="contained"
        color="primary"
        >
        게시글 등록
      </PostButton>
    </div>
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
const NoArticle = styled.div`
  margin: 50px 0 0 10%;
  font-size: 24px;
  font-weight: bold;
`

const PostButton = styled(Button)`
  margin: 20px 10%;
  font-size: 18px;

  &:hover {
    background-color: white;
    color: #283593;
  }
`