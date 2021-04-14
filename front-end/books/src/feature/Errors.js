import { useCookies } from "react-cookie"
import { useHistory } from "react-router-dom"
const { Switch } = require("react-router")

export const Errors = (error) => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"])
  const history = useHistory();
  const errorCode = error.response.code

  switch(errorCode) {
    case 401: 
      alert("로그인 시간이 초과되었습니다. 다시 로그인 해주세요")
      removeCookie("token")
      history.replace("/login")
      break
    
    case 404:
      console.log("페이지를 찾을 수 없습니다.")
      break
    
    default:
      break
  }
}