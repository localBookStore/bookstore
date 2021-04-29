import styled from "styled-components";

const MidDetail = ({description}) => {

  return <MidComponent>
    <h3>내용</h3>
    <Description>{description}</Description>
  </MidComponent>
}
export default MidDetail;

const MidComponent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 200px;
`

const Description = styled.div`
  width: 40vw;
`