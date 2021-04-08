import { useState, useEffect } from "react"
import {useCookies} from "react-cookie"
import axios from "axios"

const OrderList = ({}) => {
  const [cookies] = useCookies(["token"])
  const { token } = cookies

  useEffect(() => {
    getUserPost()
  }, [])
  
  const getUserPost = () => {
    axios.get("http://localhost:8080/api/mypage/boards", {
      headers : { Authorization: token }
    })
    .then(res => console.log(res))
    .catch(err => console.log(err.response))
  }

  return <></>
};
export default OrderList;
