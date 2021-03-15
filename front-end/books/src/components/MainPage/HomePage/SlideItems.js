import { useHistory } from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "axios"
import Slider from "react-slick"
import PrevArrow from "./CustomArrow/PrevArrow"
import NextArrow from "./CustomArrow/NextArrow"
import styled from "styled-components"

const SlideItems = () => {
  const [promoteImage, setPromoteImage] = useState(false)
  const history = useHistory();

  const clickEvent = (book) => {
    history.push({
      pathname: '/detail',
      search: `?id=${book.id}`,
      state: book
    })
  }

  useEffect(() => {
    const getPromoteImage = async () => {
      await axios.get("http://localhost:8080/api/index/image/")
        .then(res => setPromoteImage(res.data))
        .catch(err => console.log(err))
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

  return <WholeContrainer>
    <Slider {...settings} className="slider">
      {promoteImage && promoteImage.map((res, idx) => {
        return <SliderButton
          key={idx}
          className="slider-image"
          onClick={() => clickEvent(res)}
        >
          <SliderImage
            className="w-100"
            src={res.imageUrl}
            alt="First slide"
          />
        </SliderButton>
      })}
    </Slider></WholeContrainer>
}
export default SlideItems;

const WholeContrainer = styled.div`
  margin: 0 auto;
  width:80%;
  height: auto;
`

const SliderButton = styled.button`
  width:100%;
  border: 0 none;
  background-color: transparent;
`

const SliderImage = styled.img`
  height: 480px;
  object-fit: cover;
`