import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import NextArrow from "./CustomArrow/NextArrow"
import PrevArrow from "./CustomArrow/PrevArrow"
import { useState, useEffect } from "react"
import axois from "axios"
import Slider from "react-slick";
import './PickItems.css'
import axios from 'axios'

const PickItems = () => {
  const [images, setImages] = useState(false);

  useEffect(() => {
    const getImage = async () => {
      await axios.get("http://localhost:8080/api/index/wepickitem/")
        .then(res => {
          const { data } = res
          setImages(data)
        })
        .catch(err => {
          console.log(err)
        })
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
    prevArrow: <PrevArrow />,
    swipe: false,
  };

  return <div className="pick-items">
    <span className="we-pick-title">동네책방의</span><span className="we-pick-title">Pick!</span>
    <FontAwesomeIcon icon={faHandPointDown} style={{ fontSize: "60px", color: "#CC87B1" }} />

    <Slider {...settings}>
      {images && images.map((res, idx) => {
        
        return <div key={idx} className="each-image">
          <button className="random-pick-item" onClick={() => console.log(res)}>
            <img src={res.imageUrl} alt={idx} className="pick-image" />
          </button>
        </div>
      })}
    </Slider>
  </div>
}
export default PickItems;