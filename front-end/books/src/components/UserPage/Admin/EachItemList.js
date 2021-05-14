import { useState } from "react";
import axios from "axios";

import { TextField, Button, Paper } from "@material-ui/core";
import styled from "styled-components";
const ENV = process.env.NODE_ENV;

const EachItemList = ({ data, itemCheck, token }) => {
  const { id } = data;
  
  const [info, setInfo] = useState({
    ...data
  });

	const fileChangeEvent = (e) => {
		const reader = new FileReader();
		const file = e.target.files[0];
    
		reader.onloadend = () => {
      setInfo({
        ...info,
				image: reader.result,
			});
		}
    if (file)
      reader.readAsDataURL(file);
	};
  
  const updateEvent = () => {
    axios.patch("api/admin/items", {
    ...info
    }, { headers: { Authorization: token }})
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
      <div>
        { 
          ENV === 'development' ? 
          <ItemPaper component={ItemImage} src={info.imageUrl} alt={id} elevation={8} />:
          <ItemPaper component={ItemImage} src={`/image/${info.upload_image_name}`} alt={id} elevation={8} />
        }
        {/* <ItemInput type="file" accept="image/jpg,image/png,image/jpeg" onChange={fileChangeEvent} /> */}
      </div>
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
        <Div>저자: <ItemContent
          name="author" 
          variant="outlined" 
          size="small"
          defaultValue={info.author}
          onChange={eventHandler}
          /></Div>
        <Div>출판사: <ItemContent 
          name="publisher" 
          variant="outlined" 
          size="small"
          defaultValue={info.publisher}
          onChange={eventHandler}
          /></Div>
        <Div>가격: <ItemContent 
          name="price" 
          variant="outlined" 
          size="small"
          defaultValue={info.price}
          onChange={eventHandler}
          /></Div>
        <Div>수량: <ItemContent 
          name="quantity" 
          variant="outlined" 
          size="small"
          defaultValue={info.quantity}
          onChange={eventHandler}
          /></Div>
        <Div>ISBN: <ItemContent 
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
  width: 33vw;
`
const ItemPaper = styled(Paper)`
  width: 310px;
  
`
const ItemImage = styled.img`
  display: inline-block;
  width: 25vw;
  height: 50vh;
  object-fit: contain;
  margin-right: 20px;
`;
const Div = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  margin-bottom: 5px;
  font-size: 1vw;
`

const ItemContent = styled(TextField)`
  margin-left: 10px;
  width: 27vw;
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
  padding: 4px;
  font-size: 1vw;
`;