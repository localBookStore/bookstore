import axios from "axios";
import { useState, useEffect } from "react";
import { Paging } from "feature/Paging";

import styled from "styled-components";

const AllItemList = ({ location }) => {
  const token = location.state.token;
  const [items, setItems] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage] = useState(20);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/admin/items/", {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setItems(res.data);
        setIsLoading(false);
      })
      .catch((err) => console.log(err.response));
  }, []);

  return (
    <Container>
      {items && !isLoading && Paging(items, currentPage, setCurrentPage, 20)}
    </Container>
  );
};
export default AllItemList;

const Container = styled.div`
  margin: 20px;
`;
const ItemContainer = styled.div`
  margin: 10px;
`;
const ItemImage = styled.img`
  width: 200px;
  height: 350px;
  object-fit: cover;
  margin: 10px;
`;
