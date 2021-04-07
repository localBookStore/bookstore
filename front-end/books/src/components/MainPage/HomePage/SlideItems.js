import { Link } from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "axios"
import Slider from "react-slick"
import PrevArrow from "./CustomArrow/PrevArrow"
import NextArrow from "./CustomArrow/NextArrow"
import styled from "styled-components"

const SlideItems = () => {
  const [promoteImage, setPromoteImage] = useState(false)
  
  useEffect(() => {
    axios.get("http://localhost:8080/api/index/image/")
      .then(res => setPromoteImage(res.data))
      .catch(err => console.log(err))
  }, [])


  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    lazyLoad: 'ondemand',
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />
  };

  return <WholeContrainer>
    <Slider {...settings}>
      {promoteImage && promoteImage.map((book, idx) => {
        return <Link to={{pathname:`/detail/${book.id}`, state:{book}}} key={idx}>
          <SliderImage className="w-100" src={book.imageUrl} alt={idx}/>
        </Link>
      })}
    </Slider>
  </WholeContrainer>
}
export default SlideItems;

const WholeContrainer = styled.div`
  margin: 0 auto;
  width:80%;
  height: auto;
`

const SliderButton = styled.button`
  width:700px;
  border: 0 none;
  background-color: transparent;
`

const SliderImage = styled.img`
  height: 480px;
  object-fit: cover;
`