import { useState, useEffect } from "react"
import axios from "axios"

import { Table } from "react-bootstrap"
import styled from "styled-components"

const Community = ({ history }) => {
  const [articles, setArticles] = useState(null);
  const section = ["글 번호", "분류", "제목", "조회수", "작성시간"]

  useEffect(() => {
    const getBoardData = async () => {
      await axios.get("http://localhost:8080/api/board/")
        .then(res => setArticles(res.data.dtoList))
        .catch(err => console.log(err.response))
    }
    getBoardData()
  }, [])

  const summitArticle = () => {
    history.push("/community/register")
  }

  const articleDetailEvent = (id) => {
    history.push(`/community/detail/${id}`)
  }

  return <div>
    {articles && articles.length ?
      <Table hover bordered >
        <thead>
          <tr>
          {section.map((res, idx) => {
            return <td key={idx}>{res}</td>
          })}
          </tr>
        </thead>
        <tbody>
          {articles.map((res, idx) => {
            const { id, title, category, createdDate } = res
            return <tr key={idx} onClick={() => articleDetailEvent(id)}>
              <td>{id}</td>
              <td>{category}</td>
              <td>{title}</td>
              <td>0</td>
              <td>{createdDate}</td>
            </tr>
          })}
        </tbody>
      </Table>
      : <div>게시글이 없습니다.</div>}
    <button onClick={() => summitArticle()}>게시글 등록</button>
  </div >
}
export default Community;