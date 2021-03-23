import './App.css';
import Header from "./components/HeaderPage/Header"
import Home from "./components/MainPage/HomePage/Home"
import ItemDetail from "./components/MainPage/DetailPage/ItemDetail"
import BookList from "./components/MainPage/BookListPage/BookList"
import Comunity from "./components/MainPage/ComunityPage/Comunity"
import CommunityRegister from "./components/MainPage/ComunityPage/ComunityRegister"
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
        <Route exact path="/comunity" component={Comunity} />
        <Route path="/community/register" component={CommunityRegister} />
      </Switch>
    <Footer />
  </div>
}

export default App;
