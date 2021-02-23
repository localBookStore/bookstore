import './App.css';
import Header from "./components/HeaderPage/Header"
import Main from "./components/MainPage/Main"
import Footer from "./components/FooterPage/Footer"
// import {Route, Switch, NavLink} from "react-router-dom"


function App() {
  return <div className="app">
    <Header />
    <Main />
    <Footer />
  </div>
}

export default App;
