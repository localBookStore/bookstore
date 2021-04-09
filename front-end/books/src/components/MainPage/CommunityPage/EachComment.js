import { useState } from "react";
import axios from "axios";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faReply } from "@fortawesome/free-solid-svg-icons";
import { Button } from "react-bootstrap";
import styled from "styled-components";

const EachComments = ({ comment, setComments, submitEvent, boardId, token, memberEmail }) => {
	const [isUpdate, setIsUpdate] = useState(false);
	const [showInput, setShowInput] = useState(false);
	const [newContent, setNewContent] = useState(comment.content)
	const [content, setContent] = useState("");

	const modifyEvent = () => {
		axios.put("http://localhost:8080/api/board/reply/modify", {
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
			<div>
				<FontAwesomeIcon icon={faReply} rotation={180} style={{margin: "0 20px"}} />
					{ isUpdate ? <>
						<CommentInput defaultValue={newContent} onChange={e => setNewContent(e.target.value)}/>
						<CommentButton onClick={modifyEvent}>저장</CommentButton>
						<CommentButton onClick={() => setIsUpdate(false)}>취소</CommentButton>
					</>
					:
						<CommentInfo marginLeft={comment.depth}>
						{comment.content}</CommentInfo>
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
				<CommentInfo color="gray">{comment.memberEmail}</CommentInfo>
				{!isUpdate && !showInput && <>
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
