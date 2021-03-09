import styled, {keyframes} from "styled-components"

const CategoryHoverDetail = () => {
  return <HoverComponent>

    </HoverComponent>
}
export default CategoryHoverDetail;


const slideUp = keyframes`
  from {
    transform: translateY(-50px);
  }
  to {
    transform: translateY(10px);
  }
`

const HoverComponent = styled.div`
  position: absolute;
  top: 60px;
  left: 40px;

  background-color: rgba(224, 255, 239, 0.9);
  border-radius: 5px;
  width:90.5vw;
  height: 300px;

  animation-duration: 0.3s;
  animation-timing-function: ease-out;
  animation-name: ${slideUp};
`
