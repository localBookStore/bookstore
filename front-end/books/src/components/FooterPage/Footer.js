import styled from "styled-components"

const Footer = () => {
  return <FooterContainer>
    {/* <span className="phone-number">연락처: 010-1234-5678</span> */}
    <TeamTitle>Made By. 동네책방</TeamTitle>
  </FooterContainer>
}
export default Footer;


const FooterContainer = styled.div`
  position: relative;
  height: 88px;
`

const TeamTitle = styled.p`
  position: absolute;
  font-size: 12px;
  margin: 0 auto;
  top: 50%;
  left:50%;
  font-weight: 800;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  transform: translate(-50%, -50%);
  color: #777;
`