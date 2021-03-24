import './App.css';
import Header from "./components/HeaderPage/Header"
import Home from "./components/MainPage/HomePage/Home"
import ItemDetail from "./components/MainPage/DetailPage/ItemDetail"
import BookList from "./components/MainPage/BookListPage/BookList"
import Community from "./components/MainPage/ComunityPage/Community"
import CommunityRegister from "./components/MainPage/ComunityPage/CommunityRegister"
import CommunityDetail from "./components/MainPage/ComunityPage/CommunityDetail"
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
        <Route exact path="/community" component={Community} />
        <Route exact path="/community/detail/:id" component={CommunityDetail} />
        <Route path="/community/register" component={CommunityRegister} />
      </Switch>
    <Footer />
  </div>
}

export default App;
