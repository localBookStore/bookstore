import { useForm } from "react-hook-form";
import axios from "axios";

import styled from "styled-components";
import { Button } from "react-bootstrap";

const EachItemList = ({ data, itemCheck, token }) => {
  const { register, handleSubmit } = useForm();
  const { id, imageUrl, category_id, name, description, author, publisher, price, quantity, isbn } = data;

  const updateEvent = (e, imageUrl, id) => {
    setTimeout(() => {
      axios.put(
          "http://localhost:8080/api/admin/items",
          { ...e,
            imageUrl,
            id },
          { headers: { Authorization: token }}
        )
        .then((res) => alert("수정되었습니다."))
        .catch((err) => console.log(err.response.config));
    }, 200);
  };

  return (
    <form onSubmit={handleSubmit((e) => updateEvent(e, imageUrl, id))}>
      <EachItem>
        <CheckBoxInput type="checkbox" onChange={(e) => itemCheck(id, e.target.checked)} />
        <ItemImage src={imageUrl} alt={id} />

        카테고리 ID: <ItemContent defaultValue={category_id} ref={register} name="category_id" />
        <ContentContainer>
          제목: <ItemContent defaultValue={name} ref={register} name="name" />
          설명: <ItemTextArea defaultValue={description} ref={register} name="description" />
          저자: <ItemContent defaultValue={author} ref={register} name="author" />
          출판사: <ItemContent defaultValue={publisher} ref={register} name="publisher" />
          가격: <ItemContent defaultValue={price} ref={register} name="price" />
          수량: <ItemContent defaultValue={quantity} ref={register} name="quantity" />
          ISBN: <ItemContent defaultValue={isbn} ref={register} name="isbn" />
          <EditButton type="submit" variant="success">
            수정하기
          </EditButton>
        </ContentContainer>
      </EachItem>
    </form>
  );
};
export default EachItemList;

const ContentContainer = styled.div`
  height: auto;
`;
const ItemTextArea = styled.textarea`
  display: block;
  width: 500px;
  height: 300px;
`;

const ItemImage = styled.img`
  display: block;
  height: 350px;
  object-fit: cover;
  margin: 10px;
`;

const ItemContent = styled.input`
  display: block;
  width: 500px;
`;

const EachItem = styled.div``;

const CheckBoxInput = styled.input`
  zoom: 2;
`;

const EditButton = styled(Button)``;
