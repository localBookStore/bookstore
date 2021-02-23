import Slider from "react-slick"
import PrevArrow from "./CustomArrow/PrevArrow"
import NextArrow from "./CustomArrow/NextArrow"
import "./SlideItems.css"

const SlideItems = () => {
  const arr = ["tech", "nature", "animals"]
  const settings = {
    autoplay: true,
    arrows: true,
    infinite: true,
    speed: 1000,
    autoplaySpeed: 2200,
    slidesToShow: 1,
    slidesToScroll: 1,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />
  };
  return <Slider {...settings} className="slider">
        {arr.map((value, idx) => {
          return <div key={idx}>
          <img
            className="w-100"
            src={`https://placeimg.com/720/280/${value}`}
            alt="First slide"
          />
          </div>
        })}
    </Slider>
}
export default SlideItems;