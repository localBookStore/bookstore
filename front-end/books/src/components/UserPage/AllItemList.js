import axios from "axios";
import { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

import styled from "styled-components";

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

  const ShowCurrentData = () => {
    const startIdx = currentPage * 10;
    const currentData = items.slice(startIdx, startIdx + 10);

    return (
      <ItemContainer>
        {currentData.map((data, idx) => {
          return (
            <EachItem key={idx}>
              {/* <CheckBoxInput type="checkbox" onChange={() => isChecked(data.id, )}/> */}
              <ItemImage src={data.imageUrl} alt={idx}></ItemImage>
              <ItemName>{data.name}</ItemName>
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
const CheckBoxInput = styled.input``;

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
