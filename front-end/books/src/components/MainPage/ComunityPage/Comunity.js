import {useState, useEffect} from "react"
import axios from "axios"

const Comunity = ({history}) => {
  const [articles, setArticles] = useState(null);

  useEffect(() => {
    const getBoardData = async () => {
      await axios.get("http://localhost:8080/api/board/")
        .then(res => setArticles(res.data.dtoList))
        .catch(err => console.log(err.response))
    }
    getBoardData()
    
  }, [])

  const summitArticle = () => {
    history.push("/community/register")
  }

  return <div>
    {articles === [] ? <div> 나이스? </div> : <div>게시글이 없습니다.</div>}
    <button onClick={() => summitArticle()}>게시글 등록</button>
  </div>
}
export default Comunity;