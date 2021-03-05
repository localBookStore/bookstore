import { useHistory } from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "axios"
import Slider from "react-slick"
import PrevArrow from "./CustomArrow/PrevArrow"
import NextArrow from "./CustomArrow/NextArrow"
import "./SlideItems.css"

const SlideItems = () => {
  const [promoteImage, setPromoteImage] = useState(false)
  const history = useHistory();

  const GoItemDetail = (idx) => {
    history.push('/detail')
  }

  useEffect(() => {
    const getPromoteImage = async () => {
      await axios.get("http://localhost:8080/api/index/image/")
        .then(res => {
          const {data} = res
          setPromoteImage(data)
        })
        .catch(err => {
          console.log(err)
        })
    }
    getPromoteImage()
  }, [])


  const settings = {
    // className: "center",
    // centerPadding: "60px",
    // centerMode: true,
    fade: true,
    pauseOnHover: true,
    dots: true,
    autoplay: true,
    arrows: true,
    infinite: true,
    speed: 1000,
    autoplaySpeed: 2500,
    slidesToShow: 1,
    slidesToScroll: 1,
    swipeToSlide: false,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />
  };

  return <div>
    <Slider {...settings} className="slider">
      {promoteImage && promoteImage.map((value, idx) => {
        return <button
          key={idx}
          className="slider-image"
          onClick={() => { GoItemDetail(idx) }}
        >
          <img
            className="w-100"
            src={value.imageUrl}
            alt="First slide"
          />
        </button>
      })}
    </Slider></div>
}
export default SlideItems;