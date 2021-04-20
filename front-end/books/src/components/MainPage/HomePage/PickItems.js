import { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import Slider from "react-slick";
import axios from 'axios'

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import NextArrow from "./CustomArrow/NextArrow"
import PrevArrow from "./CustomArrow/PrevArrow"
import { Image } from "react-bootstrap"
import styled from "styled-components"

const PickItems = () => {
  const [images, setImages] = useState(false);

  useEffect(() => {
    const getImage = async () => {
      await axios.get("api/index/wepickitem/")
        .then(res => setImages(res.data))
        .catch(err => console.log(err))
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

  return <Container>
    <ContainerTitle>동네책방의</ContainerTitle><ContainerTitle>Pick!</ContainerTitle>
    <FontAwesomeIcon icon={faHandPointDown} style={{ fontSize: "60px", color: "#CC87B1" }} />

    <Slider {...settings}>
      {images && images.map((book, idx) => {
        return <EachBook key={idx}>
          <BookButton>
            <Link to={{pathname:`/detail/${book.id}`, state:{book}}}>
              <ImageCard rounded src={book.imageUrl} alt={idx} />
            </Link>
          </BookButton>
        </EachBook>
      })}
    </Slider>
  </Container>
}
export default PickItems;

const Container = styled.div`
  position: relative;
  margin: 0 auto;
  width: 90%;
`

const ContainerTitle = styled.span`
  font-size: 2.5rem;
  font-weight:900;
  position:relative;
  top: -10px;
  margin-right:20px;
`

const EachBook = styled.div`
  position: relative;
  overflow: hidden;
  max-height: 400px;
`

const BookButton = styled.button`
  border: 0 none;
  background-color: transparent;
`
const ImageCard = styled(Image)`
  width: 200px;
  height: 300px;
  object-fit: cover;
`