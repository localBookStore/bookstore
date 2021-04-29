import { useState } from "react";
import { useCookies } from "react-cookie"
import axios from "axios";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faReply } from "@fortawesome/free-solid-svg-icons";
import { Button, TextField } from "@material-ui/core"
import styled from "styled-components";
import jwtDecode from "jwt-decode";

const EachComments = ({ comment, setComments, submitEvent, boardId }) => {
	const [cookies] = useCookies(["token"]);
	const token = cookies.token;
	const { sub } = jwtDecode(token);
	const [isUpdate, setIsUpdate] = useState(false);
	const [showInput, setShowInput] = useState(false);
	const [newContent, setNewContent] = useState(comment.content)
	const [content, setContent] = useState("");

	const modifyEvent = () => {
		
		axios.put("api/board/reply/modify", {
			memberEmail: sub,
			content: newContent,
			id: comment.id,
			boardId,
		}, { headers : { Authorization: token }})
		.then(res => {
			setComments(res.data)
			setIsUpdate(false)
		})
		.catch(err => console.log(err.response))
	};

	const deleteEvent = (id) => {
		axios.delete("http://localhost:8080/api/board/reply/delete", {
				data: { id, boardId },
				headers: { Authorization: token },
			})
			.then((res) => setComments(res.data))
			.catch((err) => console.log(err.response));
	};
	return (
		<Container>
			<div>
					{ isUpdate ? <InputContainer>
						<CommentInput marginleft={comment.depth} variant="outlined" size="small" defaultValue={newContent} onChange={e => setNewContent(e.target.value)}/>
						<CommentButton fontcolor="#ff8a65" onClick={modifyEvent}>저장</CommentButton>
						<CommentButton fontcolor="#9e9e9e" onClick={() => setIsUpdate(false)}>취소</CommentButton>
					</InputContainer>
					: 
					<>
						<CommentContent marginleft={comment.depth}>
						<FontAwesomeIcon icon={faReply} rotation={180} style={{margin: "0 20px"}} />
						{comment.content}</CommentContent>

						{showInput && <InputContainer>
							<CommentInput variant="outlined" marginleft={comment.depth+1} size="small" onChange={e => setContent(e.target.value)} />
								<CommentButton 
									variant="outlined" 
									fontcolor="#ff8a65"
									onClick={() => {
										submitEvent(comment.depth+1, comment.id, content)
										setShowInput(false)	
									}}
									>
									답글달기
								</CommentButton>
								
								<CommentButton 
									variant="outlined" 
									fontcolor="#9e9e9e"
									onClick={() => setShowInput(false)}
									>
									취소
								</CommentButton>
						</InputContainer>}
					</>
					}
			</div>
			<div>
				<CommentInfo color="gray">{comment.modifiedDate}</CommentInfo>
				<CommentInfo color="gray">{comment.nickName}</CommentInfo>
				{token !== undefined && jwtDecode(token).sub === comment.memberEmail && !isUpdate && !showInput && 
				<>
					<CommentButton variant="outlined" fontcolor="#5c6bc0" onClick={() => setShowInput(true)}>답글</CommentButton>
					<CommentButton variant="outlined" fontcolor="#7cb342" onClick={() => setIsUpdate(true)}>수정</CommentButton>
					<CommentButton variant="outlined" fontcolor="#b71c1c" onClick={() => deleteEvent(comment.id)}>삭제</CommentButton>
				</>
				}
			</div> 
		</Container>
	);
};
export default EachComments;

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	
	margin: 10px 0;
`;

const CommentInfo = styled.span`
	margin-right: 15px;
`;
const CommentContent = styled.span`
	color: ${(props) => props.color || "black"};
	left: 0px;
	margin-left: ${props => "0px" && `${props.marginleft*30}px`};
`;
const CommentInput = styled(TextField)`
	width: 400px;
	margin-top: 10px;
	margin-left: ${props => "0px" && `${20+props.marginleft*30}px`};
`;
const CommentButton = styled(Button)`
	margin: 0 5px;
	font-size: 16px;
	color: ${props => props.fontcolor};
	background-color: white;

	&:hover{
		background-color: ${props => props.fontcolor};
		color: white;
	}
`;
const InputContainer = styled.div`
	display: flex;
	justify-content: flex-start;
	align-items: center;
`