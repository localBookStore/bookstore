import styled from "styled-components"

const Footer = () => {
  return <FooterContainer>
    <ClassifyLine />
    <TeamTitle>Made By. 동네책방</TeamTitle>
  </FooterContainer>
}
export default Footer;

const FooterContainer = styled.div`
  display: center;
  height: 100px;
  text-align: center;
  align-items: center;
  `

const TeamTitle = styled.span`
  top:50%;
  left: 50%;
  transform: translate(-50%, -50%);

  vertical-align: center;
  font-size: 14px;
  font-weight: 800;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  color: #777;
`
const ClassifyLine = styled.hr`
  border: 2px solid #787787;
  background-color: #787787;
`