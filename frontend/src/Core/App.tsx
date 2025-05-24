import './App.css'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import '@ant-design/v5-patch-for-react-19';

import Header from "../components/Header/Header";
import Footer from '../components/Footer/Footer';
import AppRouter from '../components/AppRoutes';
import { useContext } from 'react';
import { Context } from '../main'


function App() {
  const { store } = useContext(Context);

  store.checkAuth();

  console.log(store.isAuth);

  return (
    <BrowserRouter>
      <Header />
      <main>
        <AppRouter />
      </main>
      <Footer />
    </BrowserRouter>
  )
}

export default App
