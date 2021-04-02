import axios from "axios";
import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

import styled from "styled-components";

const AllItemList = ({ location }) => {
  const token = location.state.token;
  const [items, setItems] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/admin/items/", {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => setItems(res.data))
      .catch((err) => console.log(err.response));
  }, []);

  const pageChangeEvent = ({ selected }) => {
    setCurrentPage(selected);
  };

  const ShowCurrentData = () => {
    const startIdx = currentPage * 10;
    const currentData = items.slice(startIdx, startIdx + 10);

    return (
      <ItemContainer>
        {currentData.map((data, idx) => {
          return (
            <EachItem>
              <ItemImage src={data.imageUrl} alt={idx}></ItemImage>
              <ItemName>{data.name}</ItemName>
            </EachItem>
          );
        })}
      </ItemContainer>
    );
  };

  return (
    <Container>
      {items && (
        <>
          <ShowCurrentData />
          <ReactPaginate
            pageCount={Math.ceil(items.length / 10)}
            pageRangeDisplayed={10}
            marginPagesDisplayed={0}
            previousLabel="prev"
            nextLabel="next"
            containerClassName={"pagination"}
            activeClassName={"active"}
            onPageChange={pageChangeEvent}
          />
        </>
      )}
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
const EachItem = styled.div``;
const ItemName = styled.span``;
