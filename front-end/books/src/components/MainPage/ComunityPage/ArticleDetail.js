import {Button} from "react-bootstrap"
import styled from "styled-components";

const ArticleDetail = ({props}) => {
  const {category, content, createdDate, title} = props

  return <ArticleContainer>
    <ArticleTag>{category}</ArticleTag>
    <ArticleTitle>{title}</ArticleTitle>
    <hr />
    <ArticleContent>{content}</ArticleContent>
    <ArticleDate>{createdDate}</ArticleDate>
    <ButtonFeature>
      <EditButton variant="primary">수정</EditButton>
      <EditButton variant="danger">삭제</EditButton>
    </ButtonFeature>
  </ArticleContainer>
}
export default ArticleDetail;

const ArticleContainer = styled.div`
`
const ArticleTitle = styled.h2`
  margin: 10px 0 0 0;
  font-size: 50px;
  font-weight: bold;
`
const ArticleContent = styled.div`
  padding: 20px;
  font-size: 20px;
`
const ArticleDate = styled.div`
  text-align: right;
  font-size: 14px;
  color:#A1A2AD;

  margin: 20px 0 0px 0;
`
const ArticleTag = styled.div`
  color: gray;
`
const ButtonFeature = styled.div`
  display: flex;
  justify-content: flex-end;

  margin-top: 30px;
`
const EditButton = styled(Button)`
  margin: 0 20px;
`