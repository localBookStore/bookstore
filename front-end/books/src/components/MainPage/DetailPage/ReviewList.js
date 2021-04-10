import ReactStars from "react-rating-stars-component"
import { useState, useEffect } from "react"
import axios from "axios"
import Review from "./Review"

import { Button } from "react-bootstrap"
import styled from "styled-components";
import React from "react"
import { useCookies } from "react-cookie"
import jwtDecode from "jwt-decode"

const BottomDetail = ({ book }) => {
  const [cookies] = useCookies(["token"])
  const token = cookies.token

  const [reviews, setReviews] = useState([])
  const [content, setContent] = useState("")
  const [score, setScore] = useState(0)

  useEffect(() => {
    getReviewList()
  }, [])

  const getReviewList = () => {
    axios.get(`http://localhost:8080/api/items/reviews/${book.id}`)
      .then(res => setReviews(res.data))
      .catch(err => console.log(err.response))
  }

  const submitEvent = () => {
    const { sub } = jwtDecode(token)
    axios.post("http://localhost:8080/api/items/register/review", {
      itemId:book.id,
      memberEmail: sub,
      content,
      score,
    }, { 
      headers: { Authorization: token }})
    .then(res => setReviews(res.data))
    .catch(err => console.log(err.response))
  }

  const config = {
    size: 30,
    char: "",
    activeColor: "#58A677",
    onChange: newValue => setScore(newValue)
  }

  return <Container>
    { reviews.length ? reviews.map((review, idx) => {
      return <Review review={review} itemId={book.id} token={token} setReviews={setReviews} key={idx} />
    }) : <div>리뷰가 없습니다.</div>
    }
    {token !== undefined && <Wrap>
      <ReactStars {...config} />
      <ReviewInput onChange={e => setContent(e.target.value)}/>
      <SubmitButton onClick={submitEvent}>댓글쓰기</SubmitButton>
    </Wrap>}
  </Container>
}
export default BottomDetail;

const Container = styled.div`
  
`
const ReviewInput = styled.input`
  height: 8vh;
  width: 30%;

  margin: 0 20px;
`
const SubmitButton = styled(Button)`
 
`
const Wrap = styled.div`
  display: flex;
  justify-content: flex-start;
  margin-top: 20px;
`