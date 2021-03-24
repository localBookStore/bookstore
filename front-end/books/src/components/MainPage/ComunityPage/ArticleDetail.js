import styled from "styled-components";

const ArticleDetail = ({props}) => {
  const {category, content, createdDate, title} = props

  return <ArticleContainer>
    <ArticleTag>{category}</ArticleTag>
    <ArticleTitle>{title}</ArticleTitle>
    <hr />
    <ArticleContent>{content}</ArticleContent>
    <ArticleDate>{createdDate}</ArticleDate>
  </ArticleContainer>
}
export default ArticleDetail;

const ArticleContainer = styled.div`
`
const ArticleTitle = styled.h2`
  margin: 10px 0 0 10px;
  font-size: 50px;
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