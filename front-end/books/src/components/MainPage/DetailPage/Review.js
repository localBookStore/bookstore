import axios from "axios";
import jwtDecode from "jwt-decode";
import { useState } from "react";
import ReactStars from "react-rating-stars-component"

import { Button, TextField } from "@material-ui/core";
import styled from "styled-components";

const Review = ({ review, itemId, token, setReviews }) => {
	const [isUpdate, setIsUpdate] = useState(false);
  const [newContent, setNewContent] = useState(review.content)
  const [newScore, setNewScore] = useState(review.score)
	
	const deleteEvent = () => {
		axios.delete("api/items/delete/review", {
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
		console.log(review.score)
		axios.put("api/items/modify/review", {
				content: newContent,
				score: newScore,
				itemId,
				id: review.id,
			},
			{ headers: { Authorization: token } }
		)
    .then(res => {
      setReviews(res.data)
      setIsUpdate(false)
    })
    .catch(() => alert("구매자만이 리뷰를 쓸 수 있습니다."))
	};

	const Score = ({ score }) => {
		var result = "";
		for (var i = 0; i < score; i++) result += "★";
		return <span style={{color: "#ff9100"}}>{result}</span>;
	};
	
  const config = {
    size: 30,
		char: "★",
    value: newScore,
    activeColor: "#58A677",
    onChange: newValue => setNewScore(newValue)
  }
	return (
		<Container>
			<div>
				{isUpdate ? <div style={{display:"flex", justifyContent:"flex-start"}}>
            <ReactStars {...config} style={{fontSize: "1vw"}} />
						<ContentInput variant="outlined" size="small" defaultValue={newContent} onChange={e => setNewContent(e.target.value)}/>
            <SubmitButton variant="outlined" fontcolor="#66bb6a" onClick={updateEvent}>저장</SubmitButton>
            <SubmitButton variant="outlined" fontcolor="#bdbdbd" onClick={() => setIsUpdate(false)}>취소</SubmitButton>
				</div> : <>
						<Content><Score score={review.score} /></Content>
						<Content>{review.content}</Content>
				</>}
			</div>
			<div>
				<ContentInfo>{review.modifiedDate}</ContentInfo>
				<ContentInfo>{review.memberNickName}</ContentInfo>
				{token !== undefined && review.memberEmail === jwtDecode(token).sub && !isUpdate && <span>
						<SubmitButton variant="outlined" fontcolor="#fb8c00" onClick={() => setIsUpdate(true)}>수정</SubmitButton>
						<SubmitButton variant="outlined" fontcolor="#f44336" onClick={deleteEvent}>삭제</SubmitButton>
					</span>
				}
			</div>
		</Container>
	);
};
export default Review;

const Container = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;

	margin: 20px 0;
`;
const SubmitButton = styled(Button)`
	margin: 0 10px;
	font-size: 1vw;
	padding: 4px;
	color: ${props => props.fontcolor};
	background-color: white;
	font-weight: 600;
	&:hover{
		color: white;
		background-color: ${props => props.fontcolor};
	}
`;
const Content = styled.span`
	font-size: 20px;
	font-weight: 500;

	margin-right: 20px;
`;
const ContentInput = styled(TextField)`
	width: 300px;
	margin: 5px 10px;
`

const ContentInfo = styled.span`
	margin: 0 10px;
	color: gray;
`;
