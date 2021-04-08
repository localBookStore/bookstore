import { useState } from "react";
import axios from "axios";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faReply } from "@fortawesome/free-solid-svg-icons";
import { Button } from "react-bootstrap";
import styled from "styled-components";

const EachComments = ({ comment, setComments, submitEvent, boardId, token, memberEmail }) => {
	const [isModify, setIsModify] = useState(false);
	const [modifyValue, setModifyValue] = useState(comment.content);
	const [showInput, setShowInput] = useState(false);
	const [content, setContent] = useState("");

	
	console.log(comment);
	const modifyEvent = () => {
		if (!isModify) {
			setIsModify(true);
		} else {
		}
	};

	const deleteEvent = (id) => {
		axios
			.delete("http://localhost:8080/api/board/reply/delete", {
				data: { id, boardId },
				headers: { Authorization: token },
			})
			.then((res) => setComments(res.data))
			.catch((err) => console.log(err.response));
	};

	return (
		<Container>
			{/* {isModify ? <CommentInput value={modifyValue} onChange={(e) => setModifyValue(e.target.value)} /> : <CommentInfo>{comment.content}</CommentInfo>} */}
			
			<CommentInfo marginLeft={comment.depth}>
			<FontAwesomeIcon icon={faReply} rotation={180} style={{margin: "0 20px"}} />
				{comment.content}</CommentInfo>
			{showInput ? 
			<div>
					<CommentInput onChange={e => setContent(e.target.value)} />
					<Button onClick={() => {
						submitEvent(comment.depth+1, comment.id, content)
						setShowInput(false)
					}}>
						답글달기</Button>
			</div> :
			<div>
				<CommentInfo color="gray">{comment.modifiedDate}</CommentInfo>
				<CommentInfo color="gray">{comment.memberEmail}</CommentInfo>
				<CommentButton onClick={() => setShowInput(!showInput)}>답글</CommentButton>
				<CommentButton onClick={modifyEvent} variant="info">
					수정
				</CommentButton>
				<CommentButton onClick={() => {
					deleteEvent(comment.id)
					setShowInput(false)
				}} variant="danger">
					삭제
				</CommentButton>
			</div> 
			}
		</Container>
	);
};
export default EachComments;

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
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
