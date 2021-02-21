import { Carousel } from "react-bootstrap"
import "./SlideItems.css"
const SlideItems = () => {
  const arr = ["animals", "tech", "nature"]

  return <Carousel className="carousel">
    {arr.map((value, idx) => {
      return <Carousel.Item key={idx}>
        <img
          className="w-100"
          src={`https://placeimg.com/720/280/${value}`}
          alt="First slide"
        />
        <Carousel.Caption>
          <p>This is {idx+1} slide</p>
          <h3>{value.toUpperCase()} Image !</h3>
        </Carousel.Caption>
      </Carousel.Item>
    })}
  </Carousel>
}
export default SlideItems;