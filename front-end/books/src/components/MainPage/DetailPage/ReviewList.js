import ReactStars from "react-rating-stars-component"
import { useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import jwtDecode from "jwt-decode"
import axios from "axios"
import Review from "./Review"

import { TextField, Button } from '@material-ui/core'
import styled from "styled-components";

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
    axios.get(`api/items/reviews/${book.id}`)
      .then(res => setReviews(res.data))
      .catch(err => console.log(err.response))
  }

  const submitEvent = () => {
    const { sub } = jwtDecode(token)
    
    axios.post("api/items/register/review", {
      itemId:book.id,
      memberEmail: sub,
      content,
      score,
    }, { 
      headers: { Authorization: token }})
    .then(res => setReviews(res.data))
    .catch(err => alert("해당 도서의 구매자만 리뷰를 작성할 수 있습니다."))
  }

  const config = {
    size: 30,
    char: "★",
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
      <ReviewInput 
        label="Review"
        variant="outlined"
        required
        onChange={e => setContent(e.target.value)}
        />
      <SubmitButton 
        color="primary"
        variant="contained"
        onClick={submitEvent}
        >
          댓글 쓰기
        </SubmitButton>
    </Wrap>}
  </Container>
}
export default BottomDetail;

const Container = styled.div`
  
`
const ReviewInput = styled(TextField)`
  height: 50px;
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