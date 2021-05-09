import { useForm } from "react-hook-form";
import axios from "axios";

import { TextField, Button, Paper } from "@material-ui/core";
import styled from "styled-components";

const EachItemList = ({ data, itemCheck, token }) => {
  const { register, handleSubmit } = useForm();
  const { id, imageUrl, category_id, name, description, author, publisher, price, quantity, isbn } = data;

  const updateEvent = (e, imageUrl, id) => {
    setTimeout(() => {
      axios.put("api/admin/items",{ 
        ...e,
          imageUrl,
          id 
        },
        { headers: { Authorization: token }}
      )
      .then(() => alert("수정되었습니다."))
      .catch((err) => console.log(err.response.config));
    }, 200);
  };

  const ItemContext = ({value, name}) => {
    return <ItemContent 
      variant="outlined" 
      size="small" 
      value={value} 
      {...register(name)} 
      // name={name} 
      />
  }

  return (
    <form onSubmit={handleSubmit((e) => updateEvent(e, imageUrl, id))}>
      <EachItem>
        <CheckBoxInput type="checkbox" onChange={(e) => itemCheck(id, e.target.checked)} />
        <Paper component={ItemImage} src={imageUrl} alt={id} elevation={8} />

        <Contents>
          <Div>카테고리ID: <ItemContext value={category_id} name="category_id" /></Div>
          <Div>제목: <ItemContext value={name} name="name" /></Div>
          <Div>설명: <ItemTextArea multiline rows={10} variant="outlined" value={description} name="description" /></Div>
          <Div>저자: <ItemContext value={author} name="author" /></Div>
          <Div>출판사: <ItemContext value={publisher} name="publisher" /></Div>
          <Div>가격: <ItemContext value={price} name="price" /></Div>
          <Div>수량: <ItemContext value={quantity} name="quantity" /></Div>
          <Div>ISBN: <ItemContext value={isbn} name="isbn" /></Div>
        </Contents>
          <EditButton type="submit" variant="contained" color="primary">
            수정하기
          </EditButton>
      </EachItem>
    </form>
  );
};
export default EachItemList;

const Contents = styled.div`

`
const ItemTextArea = styled(TextField)`
  width: 400px;
`;

const ItemImage = styled.img`
  display: inline-block;
  height: 350px;
  object-fit: cover;
  margin-right: 20px;
`;
const Div = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin-bottom: 5px;
`

const ItemContent = styled(TextField)`
  margin-left: 10px;
  height: 40px;
  width: 400px;
`;

const EachItem = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin: 30px 0;
`;

const CheckBoxInput = styled.input`
  zoom: 2;
`;

const EditButton = styled(Button)`
  margin-left: 10px;
`;
