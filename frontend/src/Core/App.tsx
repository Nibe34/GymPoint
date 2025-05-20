import './App.css'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import '@ant-design/v5-patch-for-react-19';

import Header from "../components/Header/Header";
import Home from '../pages/Home'
import Shop from '../pages/Shop';
import Services from '../pages/Services';
import About from '../pages/About';
import Coaches from '../pages/Coaches';
import Registration from '../pages/Registration';

function App() {

  return (
    <BrowserRouter>
    <Header/>
    <main>
    <Routes>
    <Route path={'/'} element={<Home/>}/>
    <Route path={'/shop'} element={<Shop/>}/>
    <Route path={'/services'} element={<Services/>}/>
    <Route path={'/about-us'} element={<About/>}/>
    <Route path={'/coaches'} element={<Coaches/>}/>
        <Route path={'/registration'} element={<Registration/>}/>
    </Routes>
    </main>

    </BrowserRouter>
  )
}

export default App
