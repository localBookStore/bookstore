import { Link } from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "axios"
import Slider from "react-slick"
import PrevArrow from "./CustomArrow/PrevArrow"
import NextArrow from "./CustomArrow/NextArrow"

import { Image } from "react-bootstrap"
import styled from "styled-components"
const ENV = process.env.NODE_ENV;

const SlideItems = () => {
  const [promoteImage, setPromoteImage] = useState(false)
  
  useEffect(() => {
    axios.get("api/index/image/")
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
  
  return <WholeContainer>
    <Slider {...settings}>
      {promoteImage && promoteImage.map((book, idx) => {
        
        return <Link to={{pathname:`/detail/${book.id}`, state:{book}}} key={idx}>
          {
            ENV === 'development' ? 
              <ImageCard rounded className="w-100" src={book.imageUrl} alt={idx} />:
              <ImageCard rounded className="w-100" src={`/image/${book.uploadImageName}`} alt={idx}/>
          }
        </Link>
      })}
    </Slider>
  </WholeContainer>
}
export default SlideItems;

const WholeContainer = styled.div`
  margin: 0 auto;
  width:80%;
  height: auto;
`

const ImageCard = styled(Image)`
  width: auto;
  height: 500px;
  object-fit: cover;
`