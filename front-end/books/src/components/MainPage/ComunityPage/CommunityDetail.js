import axios from "axios"
import { useState, useEffect } from "react"

const CommunityDetail = ({ match }) => {
  const articleId = match.params.id;
  const [isloading, setIsloading] = useState(false);
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState(null);

  useEffect(() => {
    const getArticle = async () => {
      await axios.get(`http://localhost:8080/api/board/${articleId}/`)
        .then(res => {
          const { data } = res
          setArticle(data[0]);
          setComments(data[1]);
          setIsloading(true);
        })
        .catch(err => console.log(err.response))
    }
    getArticle();
  }, [])

  return <>
    {isloading ?
      <div>
        {console.log(article)}
        {console.log(comments)}
      </div>
      :
      <div>
        로딩중
      </div>}
  </>
}
export default CommunityDetail;