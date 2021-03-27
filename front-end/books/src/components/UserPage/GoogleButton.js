import GoogleLogin from 'react-google-login';

const clientId = process.env.REACT_APP_GOOGLE_CLIENT_KEY

const GoogleButton = ({ onSocial }) => {
  const onSuccess = async (response) => {
    console.log(response);
    console.log(onSocial)
    const { googleId, profileObj: { email, name } } = response;
    // await onSocial({
    //   socialId: googleId,
    //   socialType: 'google',
    //   email,
    //   nickname: name
    // });
  }

  const onFailure = (error) => {
    console.log(error);
  }

  return <div>
    <GoogleLogin
      clientId={clientId}
      responseType={"id_token"}
      onSuccess={onSuccess}
      onFailure={onFailure} />
  </div>
}
export default GoogleButton