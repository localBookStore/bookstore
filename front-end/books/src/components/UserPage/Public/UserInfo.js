import axios from "axios";
import { useState, useEffect } from "react";

import { Image } from "react-bootstrap";
import { TextField, Button } from "@material-ui/core";
import styled from "styled-components";
const noProfile = 'data:image/png;base64,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';


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
		const userImage = user.imageUrl;
		axios.patch("api/mypage/modify", {
        ...user,
        currentPassword: currentPassword === "" ? null : currentPassword,
        newPassword: newPassword === "" ? null : newPassword,
				imageUrl: userImage === "null" ? null : noProfile,
      }, { headers: { Authorization: state.token } })
			.then(() => alert("정보가 변경되었습니다."))
			.catch((err) => alert(err.response.data.message))
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
		const {currentPassword, newPassword, newPassword2 } = password;
		const regexr = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;

		if (user.provider === 'DEFAULT'){
			if (regexr.test(currentPassword) && newPassword === "" && newPassword2 === "") {
				modifyUser();
			} else {
				if (!regexr.test(newPassword) || !regexr.test(currentPassword)) {
					return alert("비밀번호는 8자이상 16자 이하로 영문 숫자 특수문자 조합으로 해주세요.");
				} else if (newPassword === newPassword2) {
					modifyUser();
				} else {
					return alert("새로운 비밀번호와 확인 비밀번호가 다릅니다.");
				}
			}
		}else {
			modifyUser();
		}
	};
	
	return (
		<>
			{user && (
				<Container>
					<Wrap>
						<ProfileImage src={`${user.imageUrl === 'null' ? noProfile : user.imageUrl }`} />
						<PostButton type="file" accept="image/jpg,image/png,image/jpeg" onChange={fileChangeEvent} />
					</Wrap>
					<TagContainer>
						<div style={{ display: "flex", justifyContent: "space-between" }}>
							<Tag>이메일</Tag>
							<UserContent>{user.email}</UserContent>
						</div>

						<div style={{ display: "flex", justifyContent: "space-between" }}>
							<Tag>닉네임</Tag>
							<TextBlock 
								required={true}
								defaultValue={user.nickName} 
								variant="outlined" 
								size="small" 
								name="nickName" 
								onChange={eventHandler} 
							/>
						</div>

						{user.provider === "DEFAULT" ? (
							<>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                  <Tag>현재 비밀번호</Tag>
                  <TextBlock
										required={true}
										label="Current Password" 
										type="password" size="small" 
										name="currentPassword" 
										value={password.currentPassword} 
										onChange={eventHandler} 
									/>
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
	width: 22vw;
`;

const PostButton = styled.input`
	display: block;
	margin: 0 auto;
`;

const ProfileImage = styled(Image)`
	height: 50vh;
	width: 30vw;
	object-fit: contain;
	margin: 20px;
`;

const Tag = styled.div`
	margin: auto 0;
	font-size: 1vw;
`;

const UserContent = styled.div`
	margin-bottom: 10px;
	font-size: 1.4vw;
	font-weight: bold;
`;

const TextBlock = styled(TextField)`
	width: 15vw;
`;
const EditButton = styled(Button)`
	margin-top: 30px;
`;
