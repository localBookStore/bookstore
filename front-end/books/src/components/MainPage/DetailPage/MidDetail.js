import styled from "styled-components";

const MidDetail = ({props}) => {
  const {description, category_id} = props

  return <MidComponent>
    <div>{category_id}</div>
    <Descript>{description}</Descript>
  </MidComponent>
}
export default MidDetail;

const MidComponent = styled.div`
  height: 300px;
  justify-content: left;
  display: flex;
  align-items: center;
`

const Descript = styled.div`
  position: relative;
  left: 300px;
  width: 45vw;
`