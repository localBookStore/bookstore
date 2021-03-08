import "./CategoryHoverDetail.css"

const CategoryHoverDetail = ({ page }) => {
  return <>{page === null ?
    <></> :
    <button className="hover-detail">
      카테고리바 디테일 페이지
    {page}
    </button>
  }
  </>
}
export default CategoryHoverDetail;