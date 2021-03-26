import styled from "styled-components"

const CommentsDetail = ({comments}) => {

  console.log(comments)

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