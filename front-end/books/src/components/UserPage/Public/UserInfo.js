import axios from "axios";
import { useState, useEffect } from "react";

import { Image } from "react-bootstrap";
import { TextField } from "@material-ui/core"
import styled from "styled-components";

const UserInfo = ({ location: { state }}) => {
  const [user, setUser] = useState(null);
  const [newData, setNewData] = useState({
    newNickName: "",
    newPassword1: null,
    newPassword2: null,
    image: "",
  })

  useEffect(() => {
    getUserInfo();
  }, []);

  const getUserInfo = () => {
    axios.get("api/mypage", { headers: { Authorization: state.token }})
      .then(res => setUser(res.data))
      .catch(err => console.log(err.response));
  };

  const fileChangeEvent = e => {
    const reader = new FileReader();
    const file = e.target.files[0];
    reader.readAsDataURL(file);
    
    reader.onloadend = () => {
      setNewData({
        ...newData,
        image: reader.result,
      })
    }
  }

  return <>
    { user && <Container>
      <Wrap>
        <ProfileImage 
          src="https://storage.googleapis.com/cr-resource/image/55d08c7d76975679c8749ad17fc211cf/mindcafe/700/56365ce0c143da9d10a546354f62cc75.jpg" 
        />
        <PostButton type="file" onChange={fileChangeEvent} />
      </Wrap>
        
      <TagContainer>
        <div style={{ margin: "0 20px", display: "flex", flexDirection: "column", justifyContent:"space-between"}}>
          <Tag>이메일</Tag>
          <Tag>닉네임</Tag>
          <Tag>현재 비밀번호</Tag>
          <Tag>새로운 비밀번호</Tag>
          <Tag>비밀번호 확인</Tag>
        </div>
        <div style={{ margin: "0 20px", display: "flex", flexDirection: "column", justifyContent:"space-between"}}>
          <UserContent>{user.email}</UserContent>
          <TextBlock defaultValue={user.nickName} variant="outlined" size="small"/>
          <TextBlock size="small" label="Current Password" />
          <TextBlock size="small" label="New Password" />
          <TextBlock size="small" label="New Password Confirm" />
        </div>
      </TagContainer>
    </Container>
  }
  </>
};
export default UserInfo;

const Container = styled.div`
  display: flex;
  align-items: center;
`;
const Wrap = styled.div`
  text-align: center;
`
const TagContainer = styled.div`
  display: flex;
  height: 300px;
`
const PostButton = styled.input`
  display: block;
  margin: 0 auto;
`
const ProfileImage = styled(Image)`
  width: 500px;
`
const Tag = styled.div`
  
`
const UserContent = styled.div`
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: bold;
`
const TextBlock = styled(TextField)`
  display: block;
  
`