import { useCookies } from "react-cookie";

const GetToken = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  return {token:cookies.token, setCookie, removeCookie}
}
export default GetToken;