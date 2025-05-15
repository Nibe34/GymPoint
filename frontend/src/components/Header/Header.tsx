import React from 'react';
import { NavLink } from 'react-router-dom';

import Navigation from "../Navigation/Navigation"
import Button from "../Buttons/CommonButton";
import styles from './Header.module.css'
const Header = () => {

    return (<header className = {styles.header} >
        <div className= {styles.header__container}>
            <div className="logo">
               <NavLink to={'/'}><img src="logo.svg" alt="logo"/></NavLink>
            </div>
        <Navigation/>
        <Button>Login</Button>
        </div>
    </header>)
}

export default Header;