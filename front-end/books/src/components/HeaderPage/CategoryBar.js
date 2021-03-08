import CategoryHoverDetail from "./CategoryHover/CategoryHoverDetail"
import { useState } from "react"
import { useHistory } from "react-router-dom"
import "./CategoryBar.css"

const CategoryBar = () => {
  const [isHover, setIsHover] = useState(false);
  const [HoverIdx, setHoverIdx] = useState(null);
  const history = useHistory()

  const itemNames = ["장르별", "베스트", "최신작", "커뮤니티"]

  const ShowOnHover = () => {
    setIsHover(true)
  }

  const ShowOffHover = () => {
    setIsHover(false)
    setHoverIdx(null)
  }

  const ShowHoverDetail = (page) => {
    setHoverIdx(page)
  }

  // const GoCategoryDetailPage = (category) => {
  //   history.push('/detail')
  // }

  return <div
    className="category-container"
    onMouseEnter={ShowOnHover}
    onMouseLeave={ShowOffHover}
  >
    {itemNames.map((itemName, idx) => {
      return <div
        className="item-box"
        key={idx}
      >
        <button
          className="category-item-button"
          onMouseEnter={() => ShowHoverDetail(idx)}
          // onClick={() => GoCategoryDetailPage(idx)}
        >
          {itemName}
        </button>
      </div>
    })}
    {isHover && <CategoryHoverDetail page={HoverIdx} />}
  </div>
}
export default CategoryBar