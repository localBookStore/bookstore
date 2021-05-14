import { useEffect } from "react";
import {useCookies} from "react-cookie";
import { Redirect, useHistory } from "react-router-dom";

const validation = (query) => {
  const result = query.slice(1, 6)
  let response;
  if (result === 'token'){
    response = `Bearer ${query.slice(7)}`;
  } else {
    const provider = query.slice(1)
    response = provider.slice(6)
  }
  return response
};

const OauthRedirect = ({location}) => {
  const [cookies, setCookies] = useCookies(['token']);
  const history = useHistory();

  const TOKEN = validation(location.search);
  
  useEffect(() => {
    if (!TOKEN.startsWith("Bearer ")){
      let message
      if(TOKEN !== "default") {
        message = "해당 e-mail은 " + TOKEN + " 소셜 계정입니다."
      } else {
        message = "해당 e-mail은 소셜 계정이 아닙니다.\n직접 입력해서 로그인해주세요."
      }
      alert(message)
      history.replace('/login')
    } else {
      setCookies('token', TOKEN)
    }
  }, [])

  return <Redirect to="/" />
};
export default OauthRedirect;
