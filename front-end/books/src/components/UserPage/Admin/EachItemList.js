import { useForm } from "react-hook-form";

import styled from "styled-components";
import { Button } from "react-bootstrap";

const EachItemList = ({ data, itemCheck }) => {
  const { register, handleSubmit } = useForm();
  const {
    id,
    imageUrl,
    name,
    description,
    author,
    publisher,
    price,
    quantity,
    isbn,
  } = data;

  const updateEvent = (e, imageUrl) => {};
  return (
    <form onSubmit={handleSubmit((e) => updateEvent(e, imageUrl))}>
      <EachItem>
        <CheckBoxInput
          type="checkbox"
          onChange={(e) => itemCheck(id, e.target.checked)}
        />
        <ItemImage src={imageUrl} alt={id} />
        <ContentContainer>
          제목: <ItemContent defaultValue={name} ref={register} name="name" />
          설명:{" "}
          <ItemTextArea
            defaultValue={description}
            ref={register}
            name="description"
          />
          저자:{" "}
          <ItemContent defaultValue={author} ref={register} name="author" />
          출판사:{" "}
          <ItemContent
            defaultValue={publisher}
            ref={register}
            name="publisher"
          />
          가격: <ItemContent defaultValue={price} ref={register} name="price" />
          수량:{" "}
          <ItemContent defaultValue={quantity} ref={register} name="quantity" />
          ISBN: <ItemContent defaultValue={isbn} ref={register} name="isbn" />
          <Button type="submit" variant="success">
            수정하기
          </Button>
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
  zoom: 1.5;
`;

const EditButton = styled(Button)``;
