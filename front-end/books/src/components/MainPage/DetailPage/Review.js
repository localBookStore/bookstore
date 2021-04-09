import axios from "axios";

import { Button } from "react-bootstrap";
import styled from "styled-components";

const Review = ({ review, itemId, token, setReviews }) => {
	console.log(review);

	const deleteEvent = () => {
		axios
			.delete("http://localhost:8080/api/items/delete/review", {
				data: {
					id: review.id,
					itemId,
				},
				headers: { Authorization: token },
			})
			.then((res) => setReviews(res.data))
			.catch((err) => console.log(err.response));
	};

	return (
		<Container>
			<Content>{review.content}</Content>
			<div>
        <ContentInfo>{review.modifiedDate}</ContentInfo>
        <ContentInfo>{review.memberNickName}</ContentInfo>
				<SubmitButton variant="info">수정</SubmitButton>
				<SubmitButton variant="danger" onClick={deleteEvent}>삭제</SubmitButton>
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
  font-size: 18px;
  font-weight: 500;
`

const ContentInfo = styled.span`
  margin: 0 10px;
  color: gray;
`;