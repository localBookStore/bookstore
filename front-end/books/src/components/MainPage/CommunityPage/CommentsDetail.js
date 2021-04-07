import axios from "axios"
import { useState, useEffect } from "react"

import { Button } from "react-bootstrap"
import styled from "styled-components"

const CommentsDetail = ({ comments, setComments }) => {

  const summitEvent = () => {

  }

  return <CommentContainer>
    {comments.length ?
      comments.map((res, idx) => {
        return <EachComment key={idx}>
          <CommentInfo>{res.content}</CommentInfo>
          <NestedCommentInput />
        </EachComment>
      })
      : <div>아직 댓글이 없습니다.</div>}
    <CommentInput />
    <CommentButton onClick={summitEvent}>제출</CommentButton>
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
  margin: 20px 0;
`
const CommentButton = styled(Button)`

`