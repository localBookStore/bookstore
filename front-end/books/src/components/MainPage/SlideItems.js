import Slider from "react-slick"
import PrevArrow from "./CustomArrow/PrevArrow"
import NextArrow from "./CustomArrow/NextArrow"
import "./SlideItems.css"

const SlideItems = () => {
  const arr = ["tech", "nature", "animals"]
  const settings = {
    // className: "center",
    // centerPadding: "60px",
    // centerMode: true,
    fade:true,
    pauseOnHover: true,
    dots:true,
    autoplay: true,
    arrows: true,
    infinite: true,
    speed: 1000,
    autoplaySpeed: 2500,
    slidesToShow: 1,
    slidesToScroll: 1,
    swipeToSlide:false,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />
  };

  return <div> 
  <Slider {...settings} className="slider">
        {arr.map((value, idx) => {
          return <button key={idx} className="slider-image">
          <img
            className="w-100"
            src={`https://placeimg.com/720/280/${value}`}
            alt="First slide"
          />
          </button>
        })}
    </Slider></div>
}
export default SlideItems;