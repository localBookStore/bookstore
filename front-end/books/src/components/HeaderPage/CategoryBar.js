import CategoryHoverDetail from "./CategoryHover/CategoryHoverDetail"
import { useState } from "react"
import "./CategoryBar.css"

const CategoryBar = () => {
  const [isHover, setIsHover] = useState(false);
  const [HoverIdx, setHoverIdx] = useState(null);

  const itemNames = ["국내작", "국외작", "베스트", "최신작", "커뮤니티"]

  const ShowOnHover = () => {
    setIsHover(true)
  }

  const ShowOffHover = () => {
    setIsHover(false)
  }

  const ShowHoverDetail = (page) => {
    setHoverIdx(page)
  }

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
        >
          {itemName}
        </button>
      </div>
    })}
    {isHover && <CategoryHoverDetail page={HoverIdx} />}
  </div>
}
export default CategoryBar