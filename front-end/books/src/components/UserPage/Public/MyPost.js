import { useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import axios from "axios"

import styled from "styled-components"

const OrderList = () => {
  const [articles, setArticles] = useState([])
  const [cookies] = useCookies(["token"])
  const { token } = cookies

  const getUserPost = () => {
    axios.get("http://localhost:8080/api/mypage/boards", {
      headers : { Authorization: token }
    })
    .then(res => setArticles(res.data))
    .catch(err => console.log(err.response))
  }
  useEffect(() => {
    getUserPost()
  }, [])

  return <Container>
      <TagContainer>
        <span>카테고리</span>
        <span>게시글 명</span>
        <span>내용</span>
        <span>최근 날짜</span>
      </TagContainer>
    

    {articles.length ? <div>{
      articles.map((article, idx) => {
        return <ItemContent>
          <span>{article.category}</span>
          <span>{article.title}</span>
          <span>{article.content}</span>
          <span>{article.modifiedDate}</span>
        </ItemContent>
      })
      }</div> : <div>등록된 게시글이 없습니다.</div>}
  </Container>
};
export default OrderList;

const Container = styled.div`
  width: 100%;
`
const TagContainer = styled.div`
  display: flex;
  justify-content: space-evenly;

  font-size: 22px;
  font-weight: 600;
`
const ItemContent = styled.div`
  display: flex;
  justify-content: space-evenly;


  margin: 10px 0;
`