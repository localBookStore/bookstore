import { useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import axios from "axios"
import { useHistory } from "react-router-dom";

import { Table } from "react-bootstrap";
import styled from "styled-components"

const UserArticle = ({ userInfo }) => {
  const [userArticles, setUserArticles] = useState([])
  const history = useHistory();
  const [ cookies ] = useCookies(["token"])
  const { token } = cookies
  
  useEffect(() => {
    axios.post("api/admin/member/board", {
      memberEmail: userInfo.email
    }, { headers: { Authorization: token }})
    .then(res => setUserArticles(res.data))
    .catch(err => console.log(err.response))
  }, [userInfo.email])

  const changeUrl = (id) => {
    history.replace(`/community/detail/${id}`)
  }

  return <Container>
    {userArticles.length ? 
      <Table response hover style={{width:"45vw"}}>
        <thead>
          <Tr>
            <th>글번호</th>
            <th>카테고리</th>
            <th>제목</th>
            <th>댓글 수</th>
            <th>최근 수정일</th>
          </Tr>
        </thead>
        <tbody>
          {userArticles.map((article, idx) => {
            return <Tr key={idx} onClick={() => changeUrl(article.id)}>
            <td>{article.id}</td>
            <td>{article.category}</td>
            <td>{article.title}</td>
            <td>{article.replyCount}</td>
            <td>{article.modifiedDate}</td>
          </Tr>
          })}
        </tbody>
      </Table>
     : <NotPost>유저가 등록한 게시글이 없습니다 💧</NotPost>}
  </Container>
}
export default UserArticle;

const Container = styled.div`
  margin: 0 0 0 6vw;
`
const Tr = styled.tr`
  text-align: center;
  font-size: 1vw;
  
`
const NotPost = styled.div`
  font-size: 22px;
  font-weight: 500;
`