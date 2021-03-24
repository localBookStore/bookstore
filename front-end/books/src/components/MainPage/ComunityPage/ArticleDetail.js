import axios from "axios";
import { useState, useEffect } from "react"

import { Button, Modal } from "react-bootstrap"
import styled from "styled-components";

const ArticleDetail = ({ props }) => {
  const [isDelete, setIsDelete] = useState(false);
  const [isShow, setIsShow] = useState(false);
  const { category, content, createdDate, title, id } = props

  useEffect(() => {
    if (isDelete) {
      axios.delete(`http://localhost:8080/api/board/${id}/`)
        .then(res => console.log("삭제되었습니다."))
        .catch(err => console.log(err.response))
    }
    return setIsDelete(false)
  }, [isDelete])

  const DeleteCheckModal = () => {
    return <Modal show={isShow} onHide={() => setIsShow(false)}>
      <Modal.Header closeButton>
        <Modal.Title>정말로 삭제하시겠습니까?</Modal.Title>
      </Modal.Header>
      <Modal.Body>글을 삭제하시면 내용과 함께 작성한 댓글까지 삭제됩니다.</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={() => setIsShow(false)}>
          취소</Button>
        <Button variant="danger" onClick={() => {
          setIsShow(false)
          setIsDelete(true)
        }}>
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
    <ButtonFeature>
      <EditButton variant="primary">수정</EditButton>
      <EditButton variant="danger" onClick={() => setIsShow(true)}>삭제</EditButton>
    </ButtonFeature>
    {isShow && <DeleteCheckModal />}

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