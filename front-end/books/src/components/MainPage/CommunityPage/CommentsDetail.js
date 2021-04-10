import axios from "axios"
import { useState, useEffect } from "react"
import EachComment from "./EachComment"
import { jwtDecode } from "feature/JwtDecode"

import { Button } from "react-bootstrap"
import styled from "styled-components"

const CommentsDetail = ({ comments, setComments, boardId, token }) => {
  // const memberEmail = jwtDecode(token).sub
  const [content, setContent] = useState("");

  const submitEvent = (depth, parent, content) => {
    axios.post("http://localhost:8080/api/board/reply/comment", {
      // memberEmail,
      boardId,
      depth,
      parent,
      content,
    }, { headers: { Authorization: token }})
    .then(res => {
      setComments(res.data)
      setContent("")
    })
    .catch(err => console.log(err.response))
  }
  

  return <CommentContainer>
    {comments.length ? comments.map((comment, idx) => {
      return <EachComment
        // memberEmail={memberEmail}
        comment={comment} 
        setComments={setComments}
        submitEvent={submitEvent}
        boardId={boardId}
        token={token}
        key={idx} />
      }) : 
      <div>아직 댓글이 없습니다.</div>}

    <CommentInput value={content} onChange={e => setContent(e.target.value)} />
    <CommentButton onClick={() => submitEvent(0, 0, content)}>등록</CommentButton>
  </CommentContainer>
}
export default CommentsDetail;

const CommentContainer = styled.div`

`
const CommentInput = styled.input`
  margin: 20px 0;
`
const CommentButton = styled(Button)`

`