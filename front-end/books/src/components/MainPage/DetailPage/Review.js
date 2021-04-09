import axios from "axios";
import jwtDecode from "jwt-decode";
import { useState } from "react";
import ReactStars from "react-rating-stars-component"

import { Button } from "react-bootstrap";
import styled from "styled-components";

const Review = ({ review, itemId, token, setReviews }) => {
	const [isUpdate, setIsUpdate] = useState(false);
  const [newContent, setNewContent] = useState(review.content)
  const [newScore, setNewScore] = useState(review.score)
	const { sub } = jwtDecode(token);

	const deleteEvent = () => {
		axios.delete("http://localhost:8080/api/items/delete/review", {
				data: {
					id: review.id,
					itemId,
				},
				headers: { Authorization: token },
			})
			.then((res) => setReviews(res.data))
			.catch((err) => console.log(err.response));
	};

	const updateEvent = () => {
		axios.put("http://localhost:8080/api/items/modify/review", {
				content: newContent,
				score: review.score,
				itemId,
				id: review.id,
			},
			{ headers: { Authorization: token } }
		)
    .then(res => {
      console.log(res)
      setReviews(res.data)
      setIsUpdate(false)
    })
    .catch(err => console.log(err.response))
	};

	const Score = ({ score }) => {
		var result = "";
		for (var i = 0; i < score; i++) result += "⭐️";
		
		return <span>{result}</span>;
	};

  const config = {
    size: 30,
    value: newScore,
    activeColor: "#58A677",
    onChange: newValue => setNewScore(newValue)
  }

	return (
		<Container>
			<div>
				{isUpdate ? <>
            <ReactStars {...config} />
						<ContentInput defaultValue={newContent} onChange={e => setNewContent(e.target.value)}/>
            <SubmitButton onClick={updateEvent}>저장</SubmitButton>
            <SubmitButton onClick={() => setIsUpdate(false)}>취소</SubmitButton>
				</> : <>
						<Content><Score score={review.score} /></Content>
						<Content>{review.content}</Content>
				</>}
			</div>
			<div>
				<ContentInfo>{review.modifiedDate}</ContentInfo>
				<ContentInfo>{review.memberNickName}</ContentInfo>
				{review.memberEmail === sub && !isUpdate && <>
						<SubmitButton variant="info" onClick={() => setIsUpdate(true)}>수정</SubmitButton>
						<SubmitButton variant="danger" onClick={deleteEvent}>삭제</SubmitButton>
					</>}
			</div>
		</Container>
	);
};
export default Review;

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
`;
const SubmitButton = styled(Button)`
	margin: 10px;
`;
const Content = styled.span`
	font-size: 20px;
	font-weight: 500;

	margin-right: 20px;
`;
const ContentInput = styled.input`

`

const ContentInfo = styled.span`
	margin: 0 10px;
	color: gray;
`;
