import { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import axios from "axios"
import Slider from "react-slick";

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandPointDown } from '@fortawesome/free-solid-svg-icons'
import { Image } from "react-bootstrap"
import styled from "styled-components"
import NextArrow from "./CustomArrow/NextArrow"
import PrevArrow from "./CustomArrow/PrevArrow"
const ENV = process.env.NODE_ENV;

const MonthBooks = () => {
  const [images, setImages] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const getImage = async () => {
      await axios.get("api/index/thismonth/")
        .then(res => setImages(res.data))
        .catch(err => console.log(err))
    }
    getImage()
    return setIsLoading(true);
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

  return <Container>
    <ContainerTitle>이달의 Books!</ContainerTitle>
    <FontAwesomeIcon icon={faHandPointDown} style={{ fontSize: "60px", color: "#74ABE3" }} />

    { isLoading && <Slider {...settings}>
        {images && images.map((book, idx) => {
          return <EachBook key={idx}>
            <BookButton>
              <Link to={{pathname: `/detail/${book.id}`, state:{book}}}>
                { ENV === 'development' ? 
                  <ImageCard rounded src={book.imageUrl} alt={idx} /> :
                  <ImageCard rounded src={`/image/${book.upload_image_name}`} alt={idx}/>
                }
              </Link>
            </BookButton>
          </EachBook>
        })}
      </Slider>
      }
    </Container>
}
export default MonthBooks;


const Container = styled.div`
  position: relative;
  margin: 40px auto;
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
  width: auto;
  max-height: 400px;
  overflow: hidden;
  object-fit: cover;
  border-radius: 5px;
`

const BookButton = styled.button`
  border: 0 none;
  background-color: transparent;

  width:220px;
  height:280px; 
`
const ImageCard = styled(Image)`
  width: 200px;
  height: 300px;
  object-fit: cover;
`