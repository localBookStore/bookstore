import SlideItems from "./SlideItems"
import PickItems from "./PickItems"
import MonthBooks from "./MonthBooks"
import "./Main.css"

const Main = () => {

  return <div className="main">
    <SlideItems />
    <hr className="line" />
    <PickItems />
    <hr className="line" />
    <MonthBooks />
    <hr className="line" />
  </div>
}
export default Main