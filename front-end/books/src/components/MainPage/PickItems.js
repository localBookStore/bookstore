import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import { useState, useEffect } from "react"
import axois from "axios"
import Slider from "react-slick";
import './PickItems.css'

const PickItems = () => {
  const [images, setImages] = useState(null);
  const UNSPLASH_API_KEY = process.env.REACT_APP_UNSPLASH_API_KEY
  const URL = "https://api.unsplash.com/photos/random?w=150&h=150"

  useEffect(() => {
    const getImage = async () => {
      const { data } = await axois.get(URL, {
        params: {
          client_id: UNSPLASH_API_KEY,
          count: 12,
        }
      })
      setImages(data)
    }
    getImage()
  }, [])

  const settings = {
    arrows: true,
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 3
  };

  return <div className="pick-items">
    <h2 className="we-pick-title">동네책방의 </h2><span className="we-pick-title">Pick!</span>
      <FontAwesomeIcon icon={faHandPointDown} style={{fontSize:"60px", color:"#CC87B1"}}/>

    <Slider {...settings}>
      {images !== null && images.map((res, idx) => {
        const { urls: { small } } = res
        return <div key={idx} className="random-pick-item">
          <img src={small} alt={idx} className="pick-image" />
        </div>
      })}
    </Slider>
  </div>
}
export default PickItems;