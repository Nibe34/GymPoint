import React from 'react';
import { NavLink } from 'react-router-dom';
import { useState } from 'react';
import Navigation from "../Navigation/Navigation"

import Modal from "../Modal/Modal"
import Button from "../Buttons/CommonButton";

import styles from './Header.module.css'
const Header = () => {
            const [isOpenModal, setIsModalOpen] = useState(false)

    return (<header className = {styles.header} >
        <div className= {styles.header__container}>
            <div className="logo">
               <NavLink to={'/'}><img src="logo.svg" alt="logo"/></NavLink>
            </div>
        <Navigation/>
        <Button onClick={() => setIsModalOpen(true)}>Login</Button>
        </div>
        <Modal active={isOpenModal} setActive={setIsModalOpen}></Modal>
    </header>)
}

export default Header;