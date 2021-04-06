import axios from "axios"
import { useState, useEffect } from "react"
import styled from "styled-components"

const CommentsDetail = ({ articleId }) => {
  const [comments, setComments] = useState([]);
  useEffect(() => {
    axios.get(`http://localhost:8080/api/board/${articleId}/comment/`)
      .then(res => console.log(res))
      .catch(err => console.log(err.response))
  }, [])

  return <CommentContainer>
    {comments.length ?
      comments.map((res, idx) => {
        return <EachComment key={idx}>
          <CommentInfo>{res.content}</CommentInfo>
          <NestedCommentInput />
        </EachComment>
      })
      :
      <div>아직 댓글이 없습니다.</div>}
    <CommentInput /><CommentButton>제출</CommentButton>
  </CommentContainer>
}
export default CommentsDetail;

const CommentContainer = styled.div`

`
const EachComment = styled.div`

`
const CommentInfo = styled.span`

`
const NestedCommentInput = styled.input`
  display:block;
`
const CommentInput = styled.input`
  margin: 10px;
`
const CommentButton = styled.button`
  border: 0 none;
`