import { useState } from "react";
import { useCookies } from "react-cookie"
import axios from "axios";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faReply } from "@fortawesome/free-solid-svg-icons";
import { Button } from "react-bootstrap";
import styled from "styled-components";
import jwtDecode from "jwt-decode";

const EachComments = ({ comment, setComments, submitEvent, boardId }) => {
	const [cookies] = useCookies(["token"])
	const token = cookies.token

	const [isUpdate, setIsUpdate] = useState(false);
	const [showInput, setShowInput] = useState(false);
	const [newContent, setNewContent] = useState(comment.content)
	const [content, setContent] = useState("");

	const modifyEvent = () => {
		const memberEmail = jwtDecode(token).sub
		axios.put("api/board/reply/modify", {
			memberEmail,
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
	console.log(comment)
	return (
		<Container>
			<div>
					{ isUpdate ? <>
						<CommentInput defaultValue={newContent} onChange={e => setNewContent(e.target.value)}/>
						<CommentButton onClick={modifyEvent}>저장</CommentButton>
						<CommentButton onClick={() => setIsUpdate(false)}>취소</CommentButton>
					</>
					: <>
						<CommentInfo marginLeft={comment.depth}>
						<FontAwesomeIcon icon={faReply} rotation={180} style={{margin: "0 20px"}} />
						{comment.content}</CommentInfo>
						</>
					}
			</div>
			<div>
				{showInput && <div>
						<div>
							<CommentInput onChange={e => setContent(e.target.value)} />
							<Button onClick={() => {
								submitEvent(comment.depth+1, comment.id, content)
								setShowInput(false)
							}}>답글달기</Button>
							<Button variant="secondary" onClick={() => setShowInput(false)}>취소</Button>
						</div>
				</div>}
			</div>
			<div>
				<CommentInfo color="gray">{comment.modifiedDate}</CommentInfo>
				<CommentInfo color="gray">{comment.memberNickName}</CommentInfo>
				{token !== undefined && jwtDecode(token).sub === comment.memberEmail && !isUpdate && !showInput && 
				<>
					<CommentButton onClick={() => setShowInput(true)}>답글</CommentButton>
					<CommentButton onClick={() => setIsUpdate(true)} variant="info">수정</CommentButton>
					<CommentButton onClick={() => deleteEvent(comment.id)} variant="danger">삭제</CommentButton>				
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
	
	margin: 20px 0;
`;

const CommentInfo = styled.span`
	color: ${(props) => props.color || "black"};
	left: 0px;
	margin: 0 ${props => "0px" && `${props.marginLeft*20}px`};
`;
const CommentInput = styled.input`
	margin-left: 0;
`;
const CommentButton = styled(Button)`
	margin: 5px 10px;
`;
