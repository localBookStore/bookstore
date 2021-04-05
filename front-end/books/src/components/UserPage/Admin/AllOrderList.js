import { useState, useEffect } from "react"
import axios from "axios";

import styled from "styled-components";

const AllOrderList = ({location, match}) => {
  const [users, setUsers] = useState(null);
  const token = location.state.token;
  console.log(match)
  useEffect(() => {
    axios.get("http://localhost:8080/api/admin/members", { headers: { Authorization: token}})
      .then(res => console.log(res))
      .catch(err => console.log(err.response))
  }, [])



  return <Container>여긴 모든 주문 리스트</Container>;
};
export default AllOrderList;

const Container = styled.div``;
