import SlideItems from "./SlideItems"
import PickItems from "./PickItems"
import MonthBooks from "./MonthBooks"
import styled from "styled-components"

const Main = () => {

  return <MainContainer>
    <SlideItems />
    <HrLine />
    <PickItems />
    <HrLine />
    <MonthBooks />
    <HrLine />
  </MainContainer>
}
export default Main


const MainContainer = styled.div`
  position: relative;
  width: 100%;
`

const HrLine = styled.hr`
  position: relative;
  margin-top: 40px;
  border: 0 none;
  height: 5px;
  background-color: rgba(0, 0, 0, 0.13);
`