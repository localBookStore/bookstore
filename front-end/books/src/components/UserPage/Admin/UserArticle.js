import { useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import axios from "axios"

import styled from "styled-components"

const UserArticle = ({ userInfo }) => {
  const [userArticles, setUserArticles] = useState([])
  const [ cookies ] = useCookies(["token"])
  const { token } = cookies

  useEffect(() => {
    axios.post("api/admin/member/board", {
      memberEmail: userInfo.email
    }, { headers: { Authorization: token }})
    .then(res => setUserArticles(res.data))
    .catch(err => console.log(err.response))
  }, [])

  return <Container>
    {userArticles.length ? userArticles.map((article, idx) => {
      return <ArticleContainer key={idx}>
        <span>{article.title}</span>
      </ArticleContainer>
    }) : <div>유저가 등록한 게시글이 없습니다.</div>}
  </Container>
}
export default UserArticle;

const Container = styled.div`
  margin: 30px 0;
`
const ArticleContainer = styled.div`

`
