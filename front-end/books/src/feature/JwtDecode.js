import jwt_decode from "jwt-decode";

const jwtDecode = (token) => {
  const parsedToken = token.split(" ")[1];
  const decode = jwt_decode(parsedToken);
  return decode;
};
export default jwtDecode;