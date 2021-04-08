import { useState, useEffect } from "react"
import axios from "axios"

import styled from "styled-components";

const BottomDetail = ({itemId}) => {
  
  useEffect(() => {
    getReviewList()

  }, [])

  const getReviewList = () => {
    console.log(itemId)
    axios.get("http://localhost:8080/api/items/reviews", {itemId})
      .then(res => console.log(res))
      .catch(err => console.log(err.response))
  }

  return <BottomComponent>
    할룽?
  </BottomComponent>
}
export default BottomDetail;

const BottomComponent = styled.div`
  position: relative;
`