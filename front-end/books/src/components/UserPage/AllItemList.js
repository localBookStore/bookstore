import axios from "axios";
import { useState, useEffect, useRef } from "react";
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
          const {
            id,
            name,
            description,
            author,
            publisher,
            price,
            imageUrl,
          } = data;

          return (
            <EachItem key={idx}>
              <CheckBoxInput
                type="checkbox"
                onChange={(e) => itemCheck(id, e.target.checked)}
              />
              <ItemImage src={imageUrl} alt={idx} />
              <ContentContainer>
                제목: <ItemContent value={name} disabled={true} />
                설명: <ItemTextArea value={description} />
                저자: <ItemContent value={author} />
                출판사: <ItemContent value={publisher} />
                가격: <ItemContent value={price} />
              </ContentContainer>
              <Button variant="success">수정하기</Button>
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
const ContentContainer = styled.div`
  height: auto;
`;
const ItemContainer = styled.div`
  display: block;

  margin: 10px;
`;
const ItemTextArea = styled.textarea`
  display: block;
  width: 500px;
  height: 300px;
`;

const ItemImage = styled.img`
  height: 350px;
  object-fit: cover;
  margin: 10px;
`;

const ItemContent = styled.input`
  display: block;
  width: 500px;
`;

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
