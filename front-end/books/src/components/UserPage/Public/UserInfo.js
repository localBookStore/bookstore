import axios from "axios";
import { useState, useEffect } from "react";

import { CardContent, CardMedia } from "@material-ui/core"
import styled from "styled-components";

const UserInfo = ({ location }) => {
  const [user, setUser] = useState(null);
  
  useEffect(() => {
    getUserInfo();
  }, []);

  const getUserInfo = () => {
    axios
      .get("api/mypage", {
        headers: { Authorization: location.state.token },
      })
      .then((res) => setUser(res.data))
      .catch((err) => console.log(err.response));
  };
  
  return <>
    { user && <div style={{width: "100%"}}>
    <CardStyled variant="outlined">
      <CardMediaStyled
        image="https://storage.googleapis.com/cr-resource/image/55d08c7d76975679c8749ad17fc211cf/mindcafe/700/56365ce0c143da9d10a546354f62cc75.jpg" 
        title="User Image"
        />
      <TagContainer>
        <Tag>이메일</Tag>
        <Tag>닉네임</Tag>
      </TagContainer>

      <CardContentStyled>
        <UserContent variant="h5">{user.email}</UserContent>
        <UserContent variant="h5">{user.nickName}</UserContent>
      </CardContentStyled>
  </CardStyled>
    </div>
  }
  </>
};
export default UserInfo;

const CardStyled = styled.div`
  min-width: 300px;
  display: flex;
  justify-content: center;
  align-items: center;

`;
const CardMediaStyled = styled(CardMedia)`
  padding: 10%;
  margin: 40px;
`
const CardContentStyled = styled(CardContent)`
  text-align: center;
`
const TagContainer = styled.div`
  margin: 0 30px;
`
const Tag = styled.h6`
  margin: 30px 0;
`
const UserContent = styled.h4`
  margin: 30px 0;
`