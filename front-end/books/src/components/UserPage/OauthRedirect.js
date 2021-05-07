import { ContactlessOutlined } from "@material-ui/icons";
import getCookie from "feature/getCookie";
import {useCookies} from "react-cookie";
import { Redirect } from "react-router-dom";

const validation = (value, uri) => {
	const res = value.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	const regex = new RegExp("[\\?&]" + res + "=([^&#]*)");

	const results = regex.exec(uri);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
};

const OauthRedirect = ({ location: { search } }) => {
	const [cookies, setCookie] = useCookies(["token"]);

	const token = validation("token", search);
  setCookie('token', token)
  
	if (token === "") {
		// return <Redirect to= "/login" />
	} else {
    console.log(cookies.token)
  //   return <Redirect to='/' />
  }
  return <div></div>
};
export default OauthRedirect;
