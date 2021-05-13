import { useState } from "react";
import { useForm } from "react-hook-form";
import { useHistory } from "react-router-dom";
import axios from "axios";

import { Button, TextField } from "@material-ui/core";
import { Modal, Image } from "react-bootstrap";
import styled from "styled-components";

const nonImage = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAY1BMVEX///82OEskJz6+vsIqLUKWlp0UGDQcHzkwMkfk5eZlZ3Li4uNhYm4zNUl8fomEhY8YHDfNztLV1tkhJD0nKkFBQ1VHSVqurrVzdH+ZmqCoqa87PlBaW2mHiJL19vbv8PFOUF/lf4xeAAACvUlEQVR4nO3a63LaMBRFYdkx14YWm9KGXNq+/1O2TQjsTjLESCfDVrrWb8bjb3SERCYpvWyybYxaffv+yjsWdd1eGvVvq9lNLHBYX5r0ovZLqPDKbAn/1n7+6MJmE0m0FIYO6l7YTR2avwfxSdjtwh5Y0lKIYYO6F06inleUCONW0VYYRvQVRg2qo3C+iiQaCrvbAzFiUA2Fs8WwDiQ6Cj+lvo0bVE9h6ruwVTQVyiqWEl2FqY/ai7bC46AW/tLwFUbtRWNh6mcRRGdh6jcBh4a1UAY1n+gtTP3X4kE1FwYcGu5CWcXMQbUXFh8a/sLSC1wFQtmLOYNag/C4F3MucFUIi/ZiHcKSC1wlwoILXC3C/B9T1QizL3D1CHMvcBUJMy9wNQnzDo2qhPKNOp5Yl1DOxdGDWpkw41e/o3Bx6jPDXXPeoBoKV/fXJ1oe/oVrJNFK+NA9vvpqfqrmUPsw5qFWwmHTnNX01I59zkqYbrqzhO3ViGd6CX/czd9SVS5MP++f/yjzUYUp3f5qZ2/XVSxMaTGip2/dWoVjmiA8htA0hBJC0xBKCE1DKCE0DaGE0DSEEkLTEEoITUMoITQNoYTQNIQSQtMQSghNQyghNA2hhNA0hBJC0xBKCE1DKCE0DaGE0DSEEkLTEEoITUMoITQNoYTQNIQSQtMQSghNQyghNA2hhNA0hBJC0xBKCE1DKCE0DaGE0DSEEkLTEEoITUMoITQNoYTQNIQSQtMQSghNQyghNA2hhNA0hBJC0xBKCE1DKCE0DaGE0DSEEkLTEEoITUMoITQNoYTQNIQSQtMQSghNQyghNA2hhNA0hBJC0xBKCE1DKCE0DaGE0DSEEkLTEEoITUMo7YW7d3+n2HbnCptuWlePwLOEdYbwPxEO60u/ZkHrYYQwLetdxHY5BvjnaNle+k0z2752iP8Gy6JAexK7UH8AAAAASUVORK5CYII="

const UpdateNewItem = ({modalShow, modalShowOff, token}) => {
  const history = useHistory();
  const { register, handleSubmit } = useForm();
  const [image, setImage] = useState(nonImage);
  
  const onSubmit = data => {
    axios.post("/api/admin/items/additem", {
      ...data,
      image,
    }, { headers: {Authorization: token}})
    .then(res => history.push({
      pathname:`/detail/${res.data.id}`, 
      state: {book: res.data}
    }))
    .catch(err => console.log(err.response))
  }

  const fileChangeEvent = e => {
    const reader = new FileReader();
    const file = e.target.files[0];
    
    reader.onloadend = () => {
      setImage(reader.result)
    }
    if (file)
      reader.readAsDataURL(file);
  }

  const TextBox = ({label, name, rows, multiline}) => {
    return (
      <TextInput
        required
        label={label}
        variant="outlined"
        size="medium"
        multiline={multiline}
        rows={rows || '1'}
        {...register(name)} 
      />
    )
  }

  return <Modal
    show={modalShow}
    onHide={modalShowOff}
    size="lg"
    >
    <Modal.Header closeButton>
      <Modal.Title>
        ✏️ 새로운 물건의 정보를 입력해주세요.
      </Modal.Title>
    </Modal.Header>
    <form onSubmit={handleSubmit(onSubmit)}>
    <Modal.Body>
      <Blocks>
        <span>⚠️ 사진을 가장 먼저 첨부해주세요</span>
        <BookImage src={image.image} rounded/>
        <ImageInput 
          type="file"
          accept='image/jpg,image/png,image/jpeg'
          onChange={fileChangeEvent}
        />
        <TextBox label="카테고리 번호" name="category_id" />
        <TextBox label="책 제목" name="title" />
        <TextBox label="내용 / 설명" name="content" rows="12" multiline={true}/>
        <TextBox label="저자" name="author" />
        <TextBox label="출판사" name="publisher" />
        <TextBox label="가격" name="price" />
        <TextBox label="수량" name="count" />
        <TextBox label="ISBN" name="isbn" />
      </Blocks>

    </Modal.Body>
    <Modal.Footer>
      <PostButton variant="outlined" type="submit">상품 등록하기</PostButton>
      <CancelButton variant="outlined" onClick={modalShowOff}>
        취소하기
      </CancelButton>
    </Modal.Footer>
    </form>
  </Modal>
}
export default UpdateNewItem;

const BookImage = styled(Image)`
  margin: 10px 0;
  width: 100%;
  height: 300px;
  object-fit: contain;
`
const ImageInput = styled.input`
  display: block;
`

const Blocks = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center; 
`
const TextInput = styled(TextField)`
  width: 80%;
  margin: 10px 0;
`
const PostButton = styled(Button)`
  color: #42a5f5;
`
const CancelButton = styled(Button)`
  color: #f48fb1;
`