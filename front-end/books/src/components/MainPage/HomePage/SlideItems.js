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
    console.log(book)
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
    {console.log(promoteImage)}
    <Slider {...settings}>
      {promoteImage && promoteImage.map((res, idx) => {
        return <SliderButton
          key={idx}
          onClick={() => clickEvent(res)}
        >
          <SliderImage
            className="w-100"
            src={res.imageUrl}
            alt={idx}
          />
        </SliderButton>
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