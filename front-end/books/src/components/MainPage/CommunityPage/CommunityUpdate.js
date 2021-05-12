import axios from "axios";
import { useState } from "react";

import jwtDecode from "jwt-decode";

import { TextField, Button, Select, MenuItem} from "@material-ui/core"
import styled from "styled-components";

const CommunityUpdate = ({ history, location:{ state } }) => {
	const { token } = state;
	const [inputData, setInputData] = useState({
    ...state.data
  });

	const onChangeEvent = (e) => {
		const { name, value } = e.target;
		setInputData({
			...inputData,
			[name]: value,
		});
	};
	
	const summitEvent = () => {		
		const { sub } = jwtDecode(token);

    axios.put("api/board/modify", {
      ...inputData,
      memberEmail: sub,
    }, { headers: { Authorization: token }})
    .then(() => history.replace(`/community`))
    .catch(err => console.log(err.response));
	};

	return (
		<Container>
			<CategoryInput value={inputData.category} variant="outlined" name="category" onChange={onChangeEvent}>
				<MenuItem value="사고팝니다">사고팝니다</MenuItem>
				<MenuItem value="자유게시판">자유게시판</MenuItem>
			</CategoryInput>

			<TitleInput type="text" variant='outlined' value={inputData.title} placeholder="제목을 입력하세요" name="title" onChange={onChangeEvent} />
			<ContentInput multiline rows={20} value={inputData.content} variant='outlined' placeholder="내용을 입력하세요" name="content" onChange={onChangeEvent} />
			<div style={{display:"flex", justifyContent:"flex-end"}}>
				<SummitButton onClick={summitEvent} variant="contained">☑️ 게시글 수정</SummitButton>
			</div>
		</Container>
	);
};
export default CommunityUpdate;

const Container = styled.div`
	position: relative;
	top: 20px;
	left: 20px;
	margin: 0;

	width: 100%;
`;
const CategoryInput = styled(Select)`
	width: 15%;
	margin-right: 20px;
	vertical-align: middle;
`;

const TitleInput = styled(TextField)`
	width: 75%;
	margin-bottom: 20px;
`;

const ContentInput = styled(TextField)`
	width: 80vw;
	resize: none;
`;
const SummitButton = styled(Button)`
	margin: 30px 50px 30px 0;
	font-size: 20px;

`;
