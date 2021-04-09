import ReactStars from "react-rating-stars-component"
import { useState, useEffect } from "react"
import axios from "axios"
import Review from "./Review"

import { Button } from "react-bootstrap"
import styled from "styled-components";
import jwtDecode from "jwt-decode";

const BottomDetail = ({book, token}) => {
  const [reviews, setReviews] = useState([])
  const [content, setContent] = useState("")
  const { sub } = jwtDecode(token)

  useEffect(() => {
    getReviewList()
  }, [])

  const getReviewList = () => {
    axios.get(`http://localhost:8080/api/items/reviews/${book.id}`)
      .then(res => setReviews(res.data))
      .catch(err => console.log(err.response))
  }
  const submitEvent = () => {
    axios.post("http://localhost:8080/api/items/register/review", {
      itemId:book.id,
      memberEmail: sub,
      content,
      score: 5,
    }, { 
      headers: { Authorization: token }})
    .then(res => setReviews(res.data))
    .catch(err => console.log(err.response))
  }

  return <Container>
    { reviews.length ? reviews.map((review, idx) => {
      return <Review review={review} itemId={book.id} token={token} setReviews={setReviews} key={idx} />
    }) : <div>리뷰가 없습니다.</div>
    }
    <ReviewInput onChange={e => setContent(e.target.value)}/>
    <SubmitButton onClick={submitEvent}>댓글쓰기</SubmitButton>
  </Container>
}
export default BottomDetail;

const Container = styled.div`
  
`
const ReviewInput = styled.input`
  height: 8vh;
  width: 30%;
`
const SubmitButton = styled(Button)`
  margin: 0 20px;
`