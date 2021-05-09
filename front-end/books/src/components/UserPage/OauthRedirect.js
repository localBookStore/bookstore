import { useEffect } from "react";
import {useCookies} from "react-cookie";
import { Redirect, useHistory } from "react-router-dom";

const validation = (query) => {
  const result = query.slice(1, 6)
  let response;
  if (result === 'token'){
    response = `Bearer ${query.slice(7)}`;
  } else{
    response = 'error';
  }
	return response
};

const OauthRedirect = ({location}) => {
  const [cookies, setCookies] = useCookies(['token']); 
  const history = useHistory();

  const TOKEN = validation(location.search);

  useEffect(() => {
    if (TOKEN === 'error'){
      alert("해당 이메일 계정을 사용하시려면 일반 로그인으로 진행하셔야합니다.")
      history.replace('/login')
    } else {
      setCookies('token', TOKEN)
    }
  }, [])
  return <Redirect to="/" />
  
};
export default OauthRedirect;
