import jwt_decode from "jwt-decode";

export const jwtDecode = (token) => {
  const parsedToken = token.split(" ")[1];
  const decode = jwt_decode(parsedToken);
  console.log(decode);
  return decode;
};
