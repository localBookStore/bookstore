import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import NextArrow from "./CustomArrow/NextArrow"
import PrevArrow from "./CustomArrow/PrevArrow"
import { useState, useEffect } from "react"
import axois from "axios"
import Slider from "react-slick";
import "./MonthBooks.css"

const MonthBooks = () => {
  const [images, setImages] = useState(null);
  const PIXEL_API_KEY = process.env.REACT_APP_PIXEL_API_KEY
  const URL = "https://api.pexels.com/v1/search"

  useEffect(() => {
    const getImage = async () => {
      const { data: { photos } } = await axois.get(URL, {
        params: {
          query: "random",
          per_page: 20,
          page: 4
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
    <span className="month-book-title">이달의 Books!</span>
    <FontAwesomeIcon icon={faHandPointDown} style={{ fontSize: "60px", color: "#74ABE3" }} />

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
export default MonthBooks;