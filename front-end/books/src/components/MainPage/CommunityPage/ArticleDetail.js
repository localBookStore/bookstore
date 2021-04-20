import axios from "axios";
import { useState } from "react";
import { NavLink, useHistory } from "react-router-dom"
import { jwtDecode } from "feature/JwtDecode"

import { Button, Modal } from "react-bootstrap"
import styled from "styled-components";

const ArticleDetail = ({ article, token }) => {
  const [isShow, setIsShow] = useState(false);
  const history = useHistory();
  const { category, content, createdDate, title, id, memberEmail } = article
 
  const onClickEvent = () => {
    axios.delete("api/board/delete", {
      data: {
        id,
        memberEmail:jwtDecode(token).sub,
      }, headers: {
        Authorization:token
      }
    })
      .then(res => history.replace('/community'))
      .catch(err => console.log(err.response))
  }

  const DeleteCheckModal = () => {
    return <Modal show={isShow} onHide={() => setIsShow(false)}>
      <Modal.Header closeButton>
        <Modal.Title>정말로 삭제하시겠습니까?</Modal.Title>
      </Modal.Header>
      <Modal.Body>글을 삭제하시면 내용과 함께 작성한 댓글까지 삭제됩니다.</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={() => setIsShow(false)}>
          취소</Button>
        <Button variant="danger" onClick={onClickEvent}>
          삭제</Button>
      </Modal.Footer>
    </Modal>
  }

  return <ArticleContainer>
    <ArticleTag>{category}</ArticleTag>
    <ArticleTitle>{title}</ArticleTitle>
    <hr />
    <ArticleContent>{content}</ArticleContent>
    <ArticleDate>{createdDate}</ArticleDate>
    {token !== undefined && memberEmail === jwtDecode(token).sub && <ButtonFeature>
      <NavLink to={{pathname:"/community/update", state:{id, category, content, title}}} variant="primary">수정</NavLink>
      <EditButton variant="danger" onClick={() => setIsShow(true)}>삭제</EditButton>
    </ButtonFeature>}
    {isShow && <DeleteCheckModal />}

  </ArticleContainer>
}
export default ArticleDetail;

const ArticleContainer = styled.div`
  border: 1px solid #DFE8F2;
  border-radius: 20px;
  padding: 20px;
`
const ArticleTitle = styled.h2`
  margin: 10px 0 0 0;
  font-size: 50px;
  font-weight: bold;
`
const ArticleContent = styled.div`
  padding: 20px;
  font-size: 20px;
  height: 300px;
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