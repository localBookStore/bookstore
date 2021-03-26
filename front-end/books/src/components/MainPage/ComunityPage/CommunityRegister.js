import axios from "axios";
import { useState, useEffect } from "react"

import styled from "styled-components";

const CommunityRegister = ({ history }) => {
  const [isSummit, setIsSummit] = useState(false);
  const [inputData, setInputData] = useState({
    memberId:1,
    category: "사고팝니다",
    title: null,
    content: null,
  })

  const onChangeEvent = (e) => {
    const { name, value } = e.target
    setInputData({
      ...inputData,
      [name]: value
    })
  }
  const summitEvent = () => {
    axios.post("http://localhost:8080/api/board/", {
      ...inputData
    })
      .then(() => setIsSummit(true))
      .catch(err => console.log(err.response))
  }
  useEffect(() => {
    if (isSummit) {
      history.push("/community")
    }
    return setIsSummit(false)
  }, [isSummit])


  return <Container>
    <CategoryInput name="category" onChange={onChangeEvent}>
      <option value="사고팝니다">사고팝니다</option>
      <option value="자유게시판">자유게시판</option>
    </CategoryInput>

    <TitleInput type="text" placeholder="제목을 입력하세요" name="title"
      onChange={onChangeEvent}
    />
    <ContentInput placeholder="내용을 입력하세요" name="content"
      onChange={onChangeEvent}
    />
    <SummitButton onClick={summitEvent}>제출</SummitButton>

  </Container>
}
export default CommunityRegister;

const Container = styled.div`
  position: relative;
  top: 20px;
  left: 20px;
  margin: 0;

  width: 100%;
  height: 550px;

`
const CategoryInput = styled.select`
  position: relative;
  left:10px;

  width: 150px;
  height: 45px;

  vertical-align: middle;
`

const TitleInput = styled.input`
  position: relative;
  left: 20px;
  top: 3px;

  width:800px;
  height: 45px;
`

const ContentInput = styled.textarea`
  position: relative;
  display: inline-block;
  top: 13px;
  left:10px;

  width: 80vw;
  height: 400px;

  resize: none;
`
const SummitButton = styled.button`
  position: relative;
  float: right;
  top: 30px;
  right: 40px;

  width: 80px;
  height: 40px;
`