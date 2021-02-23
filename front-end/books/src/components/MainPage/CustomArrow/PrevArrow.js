import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowCircleLeft } from '@fortawesome/free-solid-svg-icons';
import "./PrevArrow.css"

const PrevArrow = (props) => {

  const { onClick } = props
  return <button
    className="promotion-prev-btn"
    onClick={onClick}
  ><FontAwesomeIcon icon={faArrowCircleLeft} style={{color:"rgb(43, 43, 48)"}} /></button>
}
export default PrevArrow;