import { useState } from "react";
import axios from "axios";

import { TextField, Button, Paper } from "@material-ui/core";
import styled from "styled-components";
const ENV = process.env.NODE_ENV;

const EachItemList = ({ data, itemCheck, token }) => {
  const { id, imageUrl, uploadImageName } = data;
  
  const [info, setInfo] = useState({
    ...data
  });

  const updateEvent = () => {
    axios.put("api/admin/items", {
      ...info
    }, { 
      headers: { Authorization: token }}
      )
      .then(() => alert("수정되었습니다."))
      .catch((err) => console.log(err.response));
    };
  
  const eventHandler = e => {
    const { name, value } = e.target;
    setTimeout(() => {
      setInfo({
        ...info,
        [name]:value
      })
    }, 400)
  }

  return (
    <EachItem>
      <CheckBoxInput type="checkbox" onChange={(e) => itemCheck(id, e.target.checked)} />
      <Paper component={ItemImage} src={imageUrl} alt={id} elevation={8} />
      { 
        ENV === 'development' ? 
          <Paper component={ItemImage} src={imageUrl} alt={id} elevation={8} />:
          <Paper component={ItemImage} src={`/image/${uploadImageName}`} alt={id} elevation={8}/>
      }
      <Contents>
        <Div>카테고리ID: <ItemContent 
          name="category_id" 
          variant="outlined" 
          size="small"
          defaultValue={info.category_id}
          onChange={eventHandler}
          /></Div>
        <Div>제목: <ItemContent 
          name="name" 
          variant="outlined" 
          size="small"
          defaultValue={info.name}
          onChange={eventHandler}
          /></Div>
        <Div>설명: <ItemContent 
          multiline={true} 
          rows={10} 
          name="description" 
          variant="outlined" 
          size="small"
          defaultValue={info.description}
          onChange={eventHandler}
          /></Div>
        <Div>설명: <ItemContent
          name="author" 
          variant="outlined" 
          size="small"
          defaultValue={info.author}
          onChange={eventHandler}
          /></Div>
        <Div>설명: <ItemContent 
          name="publisher" 
          variant="outlined" 
          size="small"
          defaultValue={info.publisher}
          onChange={eventHandler}
          /></Div>
        <Div>설명: <ItemContent 
          name="price" 
          variant="outlined" 
          size="small"
          defaultValue={info.price}
          onChange={eventHandler}
          /></Div>
        <Div>설명: <ItemContent 
          name="quantity" 
          variant="outlined" 
          size="small"
          defaultValue={info.quantity}
          onChange={eventHandler}
          /></Div>
        <Div>설명: <ItemContent 
          name="isbn" 
          variant="outlined" 
          size="small"
          defaultValue={info.isbn}
          onChange={eventHandler}
          /></Div>
      </Contents>
      <EditButton variant="contained" color="primary" onClick={updateEvent}>
        수정하기
      </EditButton>
    </EachItem>
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
  /* height: 40px; */
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
