import axios from "axios";
import { useState, useEffect } from "react";

import { Image } from "react-bootstrap";
import { TextField, Button } from "@material-ui/core";
import styled from "styled-components";

const UserInfo = ({ location: { state } }) => {
	const [user, setUser] = useState(null);
	const [password, setPassword] = useState({
		currentPassword: "",
		newPassword: "",
		newPassword2: "",
	});

	useEffect(() => {
		getUserInfo();
	}, []);

	const getUserInfo = () => {
		axios.get("api/mypage", { headers: { Authorization: state.token } })
			.then((res) => setUser(res.data))
			.catch((err) => console.log(err.response));
	};

	const modifyUser = () => {
		const { currentPassword, newPassword } = password;
		axios.patch("api/mypage/modify",
      {
        ...user,
        currentPassword,
        newPassword: newPassword === "" ? null : newPassword,
      }, { headers: { Authorization: state.token } })
			.then(() => alert("정보가 변경되었습니다."))
			.catch((err) => console.log(err.response))
	};

	const fileChangeEvent = (e) => {
		const reader = new FileReader();
		const file = e.target.files[0];
		
		reader.onloadend = () => {
			setUser({
				...user,
				imageUrl: reader.result,
			});
		}
		if (file)
			reader.readAsDataURL(file);
	};

	const eventHandler = (e) => {
		const { name, value } = e.target;
		if (name === "currentPassword" || name === "newPassword" || name === "newPassword2") {
			setPassword({
				...password,
				[name]: value,
			});
		} else {
			setUser({
				...user,
				[name]: value,
			});
		}
	};

	const submitEvent = () => {
		const { newPassword, newPassword2 } = password;
		const regexr = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

		if (newPassword === "" && newPassword2 === "") {
			modifyUser();
		} else {
			if (regexr.test(newPassword) && newPassword === newPassword2) {
				modifyUser();
			} else if (!regexr.test(newPassword)) {
				return alert("비밀번호는 8자이상 16자 이하로 영문 숫자 특수문자 조합으로 해주세요.");
			} else {
				return alert("새로운 비밀번호와 확인 비밀번호가 다릅니다.");
			}
		}
	};

	return (
		<>
			{user && (
				<Container>
					<Wrap>
						<ProfileImage src={`/profile/${user.imageUrl}`} />
						<PostButton type="file" accept="image/jpg,image/png,image/jpeg" onChange={fileChangeEvent} />
					</Wrap>
					<TagContainer>
						<div style={{ display: "flex", justifyContent: "space-between" }}>
							<Tag>이메일</Tag>
							<UserContent>{user.email}</UserContent>
						</div>

						<div style={{ display: "flex", justifyContent: "space-between" }}>
							<Tag>닉네임</Tag>
							<TextBlock required defaultValue={user.nickName} variant="outlined" size="small" name="nickName" onChange={eventHandler} />
						</div>

						{user.provider === "DEFAULT" ? (
							<>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                  <Tag>현재 비밀번호</Tag>
                  <TextBlock required label="Current Password" type="password" size="small" name="currentPassword" value={password.currentPassword} onChange={eventHandler} />
                </div>
								<div style={{ display: "flex", justifyContent: "space-between" }}>
									<Tag>새로운 비밀번호</Tag>
									<TextBlock
										type="password"
										size="small"
										label="New Password"
										placeholder="비밀번호를 변경하시려면 입력하세요."
										name="newPassword"
										value={password.newPassword}
										onChange={eventHandler}
									/>
								</div>

								<div style={{ display: "flex", justifyContent: "space-between" }}>
									<Tag>비밀번호 확인</Tag>
									<TextBlock
										type="password"
										size="small"
										label="New Password Confirm"
										placeholder="새로운 비밀번호와 동일하게 입력하세요."
										name="newPassword2"
										value={password.newPassword2}
										onChange={eventHandler}
									/>
								</div>
							</>
						) : null}

						<EditButton color="secondary" variant="contained" onClick={submitEvent}>
							정보수정
						</EditButton>
					</TagContainer>
				</Container>
			)}
		</>
	);
};
export default UserInfo;

const Container = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	width: 60vw; ;
`;

const Wrap = styled.div`
	text-align: center;
`;

const TagContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	width: 30vw;
`;

const PostButton = styled.input`
	display: block;
	margin: 0 auto;
`;

const ProfileImage = styled(Image)`
	height: 400px;
	width: 400px;
	object-fit: contain;
	margin: 20px;
`;

const Tag = styled.div`
	margin: auto 0;
`;

const UserContent = styled.div`
	margin-bottom: 10px;
	font-size: 20px;
	font-weight: bold;
`;

const TextBlock = styled(TextField)`
	width: 280px;
`;
const EditButton = styled(Button)`
	margin-top: 30px;
`;
