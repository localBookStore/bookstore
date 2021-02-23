import { useState, useEffect } from "react"
import axois from "axios"
import Slider from "react-slick";
import './PickItems.css'

const PickItems = () => {
  const [images, setImages] = useState(null);
  
  useEffect(() => {
    const getImage = () => {
      URL = 
      axois.get("")
    }  
  })
  const settings = {
    arrows: true,
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 3
  };
  
  return <div className="pick-items">
    <Slider {...settings}>

    </Slider>
  </div>
}
export default PickItems;