import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { BrowserRouter } from "react-router-dom"
import './index.css';

import axios from "axios"
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import { StylesProvider } from "@material-ui/core"
import { CookiesProvider } from 'react-cookie';

if (process.env.NODE_ENV === 'development'){
  axios.defaults.baseURL = 'http://localhost:8080';
}  else{
  axios.defaults.baseURL = 'https://www.books25.shop:8080';
}

ReactDOM.render(
  <BrowserRouter>
    <CookiesProvider>
      <StylesProvider injectFirst>
        <App />
      </StylesProvider>
    </CookiesProvider>
  </BrowserRouter>,
  document.getElementById('root')
);