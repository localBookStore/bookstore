import SlideItems from "./SlideItems"
import PickItems from "./PickItems"
import "./Main.css"

const Main = () => {

  return <div className="main">
    <SlideItems />
    <hr className="line" />
    <PickItems />
  </div>
}
export default Main