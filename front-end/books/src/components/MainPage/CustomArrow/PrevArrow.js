import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowCircleLeft } from '@fortawesome/free-solid-svg-icons';
import "./PrevArrow.css"

const PrevArrow = (props) => {

  const { onClick } = props
  return <button
    className="promotion-prev-btn"
    onClick={onClick}
  ><FontAwesomeIcon icon={faArrowCircleLeft} /></button>
}
export default PrevArrow;