import axios from "axios";
import { useState, useEffect } from "react";

import styled from "styled-components";

const UserList = () => {
  useEffect(() => {
    axios.get("http://localhost:8080/api");
  });

  return <Container></Container>;
};
export default UserList;

const Container = styled.div``;
