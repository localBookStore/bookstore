import { useState, useEffect } from "react"
import { NavLink } from "react-router-dom"
import axios from "axios"

import { Table, Button } from "react-bootstrap"
import styled from "styled-components"

const Community = ({ history }) => {
  const [articles, setArticles] = useState([]);
  const section = ["글 번호", "분류", "제목", "작성시간"]

  useEffect(() => {
    const getBoardData = async () => {
      await axios.get("http://localhost:8080/api/board/")
        .then(res => setArticles(res.data.dtoList))
        .catch(err => console.log(err.response))
    }
    getBoardData()
  }, [])

  const articleDetailEvent = (id) => {
    history.push(`/community/detail/${id}`)
  }

  return <div>
    {articles.length ? <Table hover bordered >
        <thead>
          <tr>{section.map((res, idx) => {
            return <td key={idx}>{res}</td>
          })}</tr>
        </thead>

        <tbody>
          {articles.map((res, idx) => {
            const { id, title, category, createdDate } = res
            const [day, time] = createdDate.split("T")
            return <tr key={idx} onClick={() => articleDetailEvent(id)}>  
              <td>{id}</td>
              <td>{category}</td>
              <td>{title}</td>
              <td>{day} {time}</td>
            </tr>
          })}
          </tbody>
      </Table>
      : <div>게시글이 없습니다.</div>}
    <NavLink to="/community/register"><Button variant="info">게시글 등록</Button></NavLink>
  </div >
}
export default Community;