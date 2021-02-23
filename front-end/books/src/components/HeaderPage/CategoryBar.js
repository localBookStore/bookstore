import "./CategoryBar.css"

const CategoryBar = () => {

  const itemNames = ["국내작", "국외작", "베스트", "최신작", "커뮤니티"]

  return <div className="category-container">
    {itemNames.map((itemName, idx) => {
      return <div className="item-box">
        <button className="category-item-button">{itemName}</button>
      </div>
    })}
  </div>
}
export default CategoryBar