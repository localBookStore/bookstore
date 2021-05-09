import axios from "axios";
import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

import EachItemList from "./EachItemList";

import styled from "styled-components";
import { Button } from "@material-ui/core";

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
    axios.delete("api/admin/items", {
      data: [...selected],
        headers: { Authorization: token },
      })
      .then((res) => {
        setItems(res.data);
        setSelected(new Set());
      })
      .catch((err) => console.log(err.response));
  };

  const ShowCurrentData = () => {
    const startIdx = currentPage * 5;
    const currentData = items.slice(startIdx, startIdx + 5);
    
    return (
      <ItemContainer>
        {currentData.map((data, idx) => {
          return<EachItemList
              data={data}
              itemCheck={itemCheck}
              token={token}
              key={idx}
            />
        })}
      </ItemContainer>
    );
  };

  return <>{items && <Container>
          <ShowCurrentData />
          <DeleteButton variant="contained" color="secondary" onClick={deleteItem}>
            체크된 아이템 삭제
          </DeleteButton>
          <ReactPaginateStyled>
            <ReactPaginate
              pageCount={Math.ceil(items.length / 5)}
              pageRangeDisplayed={10}
              marginPagesDisplayed={0}
              previousLabel="⏪"
              nextLabel="⏩"
              containerClassName={"pagination"}
              activeClassName={"active"}
              onPageChange={({ selected }) => setCurrentPage(selected)}
            />
          </ReactPaginateStyled>
        </Container>
      }</>
};
export default AllItemList;

const Container = styled.div`
  margin: 20px;
`;
const ItemContainer = styled.div`
  display: block;

  margin: 10px;
`;

const DeleteButton = styled(Button)`
  float: right;
`;

const ReactPaginateStyled = styled.div`
  .pagination {

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
