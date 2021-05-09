import { useState, useEffect } from "react";
import {useCookies} from "react-cookie";
import { Redirect } from "react-router-dom";

const validation = (uri) => {
	const result = `Bearer ${uri.slice(7)}`
	return result
};

const OauthRedirect = ({location}) => {
  const [cookies, setCookies] = useCookies(['token']); 

  const TOKEN = validation(location.search);
  useEffect(() => {
    setCookies('token', TOKEN)
  }, [])
  
  return <Redirect to="/" />
  
};
export default OauthRedirect;
