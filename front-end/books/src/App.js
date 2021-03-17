import './App.css';
import Header from "./components/HeaderPage/Header"
import Home from "./components/MainPage/HomePage/Home"
import ItemDetail from "./components/MainPage/DetailPage/ItemDetail"
import BookList from "./components/MainPage/BookListPage/BookList"
import Footer from "./components/FooterPage/Footer"
import ScrollToTop from "./ScrollToTop"
import { Route, Switch, Link } from "react-router-dom"


function App() {
  return <div className="app">
    <ScrollToTop />
    <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/detail" component={ItemDetail} />
        <Route path="/booklist" component={BookList} />
      </Switch>
    <Footer />

  </div>
}

export default App;
