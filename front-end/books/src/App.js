import './App.css';
import Header from "./components/HeaderPage/Header"
import Home from "./components/MainPage/HomePage/Home"
import ItemDetail from "./components/MainPage/DetailPage/ItemDetail"
import Footer from "./components/FooterPage/Footer"
import { Route, Switch, Link } from "react-router-dom"


function App() {
  return <div className="app">
    <Header />

    <Switch>
      <Route exact path="/" component={Home} />
      <Route path="/detail" component={ItemDetail} />
    </Switch>
      
    <Footer />
  </div>
}

export default App;
