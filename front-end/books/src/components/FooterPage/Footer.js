import styled from "styled-components"

const Footer = () => {
  return <FooterContainer>
    <ClassifyLine />
    <TeamTitle>Made By. 동네책방</TeamTitle>
  </FooterContainer>
}
export default Footer;

const FooterContainer = styled.div`
  position: relative;

  width: 100%;
  height: 88px;
  `

const TeamTitle = styled.span`
  position: absolute;
  top:50%;
  left: 50%;
  transform: translate(-50%, -50%);

  font-size: 12px;
  font-weight: 800;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  transform: translate(-50%, -50%);
  color: #777;
`
const ClassifyLine = styled.hr`
  border: 2px solid #787787;
  background-color: #787787;
`