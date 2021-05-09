import { Modal } from "react-bootstrap";
import { useForm } from "react-hook-form";
import { Button, TextField } from "@material-ui/core";

import styled from "styled-components";


const UpdateNewItem = ({modalShow, modalShowOff}) => {
  const { register, handleSubmit } = useForm();
  
  const onSubmit = data => {
    console.log(data)
  }

  const TextBox = ({label, size, name, rows, multiline}) => {
    console.log(multiline)
    return (
      <TextInput
        label={label}
        variant="outlined"
        size={size}
        multiline={multiline}
        rows={rows || 'none'}
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
    <TextField
      variant="outlined"
      size="small" 
      {...register("example")} 
    />
    <Blocks>
      <TextBox label="카테고리 번호" size="small" name="CategoryId" />
      <TextBox label="책 제목" size="small" name="title" />
      <TextBox label="내용 / 설명" size="small" name="content" rows="12" multiline={true}/>
      <TextBox label="저자" size="small" name="author" />
      <TextBox label="출판사" size="small" name="publisher" />
      <TextBox label="가격" size="small" name="price" />
      <TextBox label="수량" size="small" name="count" />
      <TextBox label="ISBN" size="small" name="isbn" />
    </Blocks>

  </Modal.Body>
  <Modal.Footer>
    <Button type="submit">상품 등록하기</Button>
    <Button color="secondary" onClick={modalShowOff}>
      취소하기
    </Button>
  </Modal.Footer>
  </form>
</Modal>
}
export default UpdateNewItem;

const Blocks = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`

const TextInput = styled(TextField)`
  width: 70%;
  margin: 20px 0;
`