import axios from "axios"
import { useState, useEffect } from "react"

import getCookie from "feature/getCookie"
import jwtDecode from "jwt-decode"

import ArticleDetail from "./ArticleDetail"
import CommentsDetail from "./CommentsDetail"

import styled from "styled-components"

const CommunityDetail = ({ match }) => {
  const articleId = match.params.id;
  const { token } = getCookie();
  const { sub } = jwtDecode(token);

  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState(null);

  useEffect(() => {
    axios.get(`api/board/${articleId}/`)
      .then(res => {
        setArticle(res.data[0]);
        setComments(res.data[1]);
      })
      .catch(err => console.log(err.response))    
  }, [])

  return <>
    {article && comments && <BoardContainer>
      <ArticleDetail 
        article={article} 
        token={token}
        email={sub}
        />
      
      <CommentsDetail 
        comments={comments} 
        setComments={setComments} 
        boardId={articleId} 
        token={token}
        email={sub}
        />
    </BoardContainer>
    }
  </>
}
export default CommunityDetail;

const BoardContainer = styled.div`
  margin: 30px;
  height:auto;
`