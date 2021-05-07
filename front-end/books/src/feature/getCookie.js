import { useCookies } from "react-cookie";

const GetCookie = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  return {token:cookies.token, setCookie, removeCookie}
}
export default GetCookie;