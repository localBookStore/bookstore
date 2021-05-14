import { useState, useEffect } from "react"
import { useCookies } from "react-cookie"
import axios from "axios"

import { TableContainer, Table, TableRow, TableHead, TableBody, TableCell, Paper } from "@material-ui/core"
import { BorderColor } from '@material-ui/icons/';
import styled from "styled-components"

const OrderList = ({ history }) => {
  const [articles, setArticles] = useState([])
  const [cookies] = useCookies(["token"])
  const { token } = cookies

  const getUserPost = () => {
    axios.get("api/mypage/boards", {
      headers : { Authorization: token }
    })
    .then(res => setArticles(res.data))
    .catch(err => console.log(err.response))
  }
  useEffect(() => {
    getUserPost()
  }, [])

  const changeUrl = (id) => {
    history.replace(`/community/detail/${id}`)
  }

  return <TableContainer component={Paper} style={{margin:"0 30px"}}>
    <Title><BorderColor fontSize="large" color="primary" /> 회원님의 쓴 글 목록입니다.</Title>
		<Table style={{width:"60vw"}}>
			<TableHead>
				<TableRow>
					<StyledHeadTableCell>카테고리</StyledHeadTableCell>
					<StyledHeadTableCell>게시글 명</StyledHeadTableCell>
					<StyledHeadTableCell>최근 날짜</StyledHeadTableCell>
				</TableRow>
			</TableHead>

      <TableBody>
        {articles.length > 0 ? articles.map((article, idx) => {
          return <TableRow hover key={idx} onClick={() => changeUrl(article.id)}>
            <StyledBodyTableCell>{article.category}</StyledBodyTableCell>
            <StyledBodyTableCell>{article.title}</StyledBodyTableCell>
            <StyledBodyTableCell>{article.modifiedDate}</StyledBodyTableCell>
          </TableRow>
        }) : null}
      </TableBody>
    </Table>
	</TableContainer>
};
export default OrderList;

const StyledHeadTableCell = styled(TableCell)`
  text-align: center;
  font-size: 20px;
  font-weight: 700;
`
const StyledBodyTableCell = styled(TableCell)`
  text-align: center;
  font-size: 1.5vw;
`

const Title = styled.div`
  font-family: 'Sunflower', sans-serif;

  font-size: 4vw;
  margin: 20px 30px;
`