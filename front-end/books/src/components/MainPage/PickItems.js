import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import NextArrow from "./CustomArrow/NextArrow"
import PrevArrow from "./CustomArrow/PrevArrow"
import { useState, useEffect } from "react"
import axois from "axios"
import Slider from "react-slick";
import './PickItems.css'

const PickItems = () => {
  const [images, setImages] = useState(null);
  const PIXEL_API_KEY = process.env.REACT_APP_PIXEL_API_KEY
  const URL = "https://api.pexels.com/v1/search"

  useEffect(() => {
    const getImage = async () => {
      const { data: { photos } } = await axois.get(URL, {
        params: {
          query: "random",
          per_page: 15,
          page: 1
        },
        headers: {
          Authorization: PIXEL_API_KEY,
        }
      })
      setImages(photos)
    }
    getImage()
  }, [])

  const settings = {
    arrows: true,
    dots: true,
    infinite: true,
    speed: 800,
    slidesToShow: 4,
    slidesToScroll: 3,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />
  };

  return <div className="pick-items">
    <span className="we-pick-title">동네책방의</span><span className="we-pick-title">Pick!</span>
    <FontAwesomeIcon icon={faHandPointDown} style={{ fontSize: "60px", color: "#CC87B1" }} />

    <Slider {...settings}>
      {images !== null && images.map((res, idx) => {
        const { src: { large } } = res
        return <div key={idx} className="each-image">
          <button className="random-pick-item" onClick={() => console.log(res)}>
            <img src={large} alt={idx} className="pick-image" />
          </button>
        </div>
      })}
    </Slider>
  </div>
}
export default PickItems;