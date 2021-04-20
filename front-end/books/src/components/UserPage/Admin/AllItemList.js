import axios from "axios";
import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

import EachItemList from "./EachItemList";

import styled from "styled-components";
import { Button } from "react-bootstrap";

const AllItemList = ({ location }) => {
  const token = location.state.token;
  const [items, setItems] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [selected, setSelected] = useState(new Set());

  useEffect(() => {
    axios.get("api/admin/items", {
      headers: {
        Authorization: token
      }
    }).then(res => setItems(res.data))
    .catch(err => console.log(err.response))
  }, [])


  const itemCheck = (itemId, isCheck) => {
    if (isCheck) selected.add(itemId);
    else selected.delete(itemId);
    setSelected(selected);
  };

  const deleteItem = () => {
    console.log([...selected]);
    axios
      .delete("api/admin/items", {
        headers: {
          Authorization: token,
        },
        data: [...selected],
      })
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
    
    return (
      <ItemContainer>
        {currentData.map((data, idx) => {
          return (
            <EachItemList
              data={data}
              itemCheck={itemCheck}
              token={token}
              key={idx}
            />
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
  display: block;

  margin: 10px;
`;

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
