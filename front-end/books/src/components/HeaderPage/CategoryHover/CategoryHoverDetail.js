import "./CategoryHoverDetail.css"

const CategoryHoverDetail = ({page}) => {
  return <>{page !== undefined ? 
    <button className="hover-detail">
    카테고리바 디테일 페이지
    {page}
  </button> : <div>없음</div> 
  }
  </>
}
export default CategoryHoverDetail;