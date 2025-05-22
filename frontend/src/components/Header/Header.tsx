import React from 'react';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import Navigation from "../Navigation/Navigation"

import Modal from "../Modal/Modal"
import LoginForm from '../Forms/LoginForm'
import Button from "../Buttons/CommonButton";

import styles from './Header.module.css'
const Header = () => {
    const [isOpenModal, setIsModalOpen] = useState(false)

    return (<header className={styles.header} >
        <div className={styles.header__container}>
            <div className="logo">
                <Link to={'/'}><img src="logo.svg" alt="logo" /></Link>
            </div>
            <div>
                <Navigation />
                <Button onClick={() => setIsModalOpen(true)}>Login</Button>
            </div>
        </div>
        <Modal active={isOpenModal} setActive={setIsModalOpen}><LoginForm setActive={setIsModalOpen}></LoginForm></Modal>
    </header>)
}

export default Header;