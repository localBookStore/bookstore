import axios from "axios"
import { useState } from "react"
import EachComment from "./EachComment"
import jwtDecode from "feature/jwtDecode"

import { Button, TextField } from "@material-ui/core"
import styled from "styled-components"

const CommentsDetail = ({ comments, setComments, boardId, token }) => {
  const [content, setContent] = useState("");

  const submitEvent = (depth, parent, content) => {
    const email = jwtDecode(token).sub
    axios.post("api/board/reply/comment", {
      memberEmail:email,
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
        comment={comment} 
        setComments={setComments}
        submitEvent={submitEvent}
        boardId={boardId}
        token={token}
        key={idx} />
      }) : 
      <Notice>😆 첫 댓글을 등록해 주세요</Notice>}

    {token === undefined ? 
    <Notice>🥺 댓글을 이용하시려면 로그인을 해주세요</Notice> :
    <>
      <CommentInput variant="outlined" size="small" value={content} onChange={e => setContent(e.target.value)} />
      <CommentButton variant="contained" onClick={() => submitEvent(0, 0, content)}>등록</CommentButton>
    </>
    }
  </CommentContainer>
}
export default CommentsDetail;

const CommentContainer = styled.div`

`
const CommentInput = styled(TextField)`
  margin: 0 20px;
  width: 50%;
`
const CommentButton = styled(Button)`
  font-size: 16px;
  font-weight: bold;
  background-color:#4db6ac;
  color: white;

  &:hover{
    color: #4db6ac;
    background-color: white;
  }
`
const Notice = styled.div`
  margin: 20px;
  font-size: 20px;
`