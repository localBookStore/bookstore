import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import NextArrow from "./CustomArrow/NextArrow"
import PrevArrow from "./CustomArrow/PrevArrow"
import { useState, useEffect } from "react"
import axios from "axios"
import Slider from "react-slick";
import "./MonthBooks.css"

const MonthBooks = () => {
  const [images, setImages] = useState(null);

  useEffect(() => {
    const getImage = async () => {
      await axios.get("http://localhost:8080/api/index/thismonth/")
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
    prevArrow: <PrevArrow />
  };

  return <div className="pick-items">
    <span className="month-book-title">이달의 Books!</span>
    <FontAwesomeIcon icon={faHandPointDown} style={{ fontSize: "60px", color: "#74ABE3" }} />

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
export default MonthBooks;