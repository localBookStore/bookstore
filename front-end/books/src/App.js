import './App.css';
import Header from "./components/HeaderPage/Header"
import Home from "./components/MainPage/HomePage/Home"
import ItemDetail from "./components/MainPage/DetailPage/ItemDetail"
import BookList from "./components/MainPage/BookListPage/BookList"
import Comunity from "./components/MainPage/ComunityPage/Comunity"
import Footer from "./components/FooterPage/Footer"

import ScrollToTop from "./ScrollToTop"
import { Route, Switch } from "react-router-dom"


function App() {
  return <div className="app">
    <ScrollToTop />
    <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/detail" component={ItemDetail} />
        <Route path="/booklist" component={BookList} />
        <Route path="/board" component={Comunity} />
      </Switch>
    <Footer />

  </div>
}

export default App;
