import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { BrowserRouter } from "react-router-dom"
import './index.css';

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import { StylesProvider } from "@material-ui/core"

ReactDOM.render(
  <BrowserRouter>
    <StylesProvider injectFirst>
      <App />
    </StylesProvider>
  </BrowserRouter>,
  document.getElementById('root')
);