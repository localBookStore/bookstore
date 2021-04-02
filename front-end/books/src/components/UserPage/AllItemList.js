import axios from "axios";
import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

import styled from "styled-components";
import { Button } from "react-bootstrap";

const AllItemList = ({ location }) => {
  const token = location.state.token;
  const [items, setItems] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [selected, setSelected] = useState(new Set());

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

  const itemCheck = (itemId, isCheck) => {
    if (isCheck) selected.add(itemId);
    else selected.delete(itemId);
    setSelected(selected);
  };

  const deleteItem = () => {
    axios
      .delete(
        "http://localhost:8080/api/admin/items/",
        {
          data: [...selected],
        },
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then((res) => {
        console.log(res);
        setItems(res.data);
        setSelected(new Set());
      })
      .catch((err) => console.log(err.response));
  };

  const ShowCurrentData = () => {
    const startIdx = currentPage * 10;
    const currentData = items.slice(startIdx, startIdx + 10);
    console.log(currentData);
    return (
      <ItemContainer>
        {currentData.map((data, idx) => {
          return (
            <EachItem key={idx}>
              <CheckBoxInput
                type="checkbox"
                onChange={(e) => itemCheck(data.id, e.target.checked)}
              />
              <ItemImage src={data.imageUrl} alt={idx}></ItemImage>
              <ContentContainer>
                <ItemContent>제목: {data.name}</ItemContent>
                <ItemContent>설명: {data.description}</ItemContent>
                <ItemContent>저자: {data.author}</ItemContent>
                <ItemContent>출판사: {data.publisher}</ItemContent>
                <ItemContent>가격: {data.price}</ItemContent>
              </ContentContainer>
            </EachItem>
          );
        })}
      </ItemContainer>
    );
  };

  return (
    <>
      {items && (
        <Container>
          <ShowCurrentData />
          <DeleteButton variant="danger" onClick={deleteItem}>
            삭제
          </DeleteButton>
          <ReactPaginateStyled>
            <ReactPaginate
              pageCount={Math.ceil(items.length / 10)}
              pageRangeDisplayed={9}
              marginPagesDisplayed={0}
              previousLabel="prev"
              nextLabel="next"
              containerClassName={"pagination"}
              activeClassName={"active"}
              onPageChange={({ selected }) => setCurrentPage(selected)}
            />
          </ReactPaginateStyled>
        </Container>
      )}
    </>
  );
};
export default AllItemList;

const Container = styled.div`
  margin: 20px;
`;
const ItemContainer = styled.div`
  float: left;
  margin: 10px;
`;
const ItemImage = styled.img`
  width: 200px;
  height: 350px;
  object-fit: cover;
  margin: 10px;
`;
const ContentContainer = styled.div``;

const ItemContent = styled.div``;

const EachItem = styled.div``;

const CheckBoxInput = styled.input``;

const DeleteButton = styled(Button)``;

const ReactPaginateStyled = styled.div`
  margin: 0 20%;
  .pagination {
    justify-content: center;

    & > li > a {
      font-size: 20px;
      margin: 10px;
    }
  }
  .active {
    background-color: #4caf50;
    color: white;
  }
`;
