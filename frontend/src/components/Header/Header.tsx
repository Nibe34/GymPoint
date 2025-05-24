import React, { useContext, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { observer } from 'mobx-react-lite';
import Navigation from '../Navigation/Navigation';
import Modal from '../Modal/Modal';
import LoginForm from '../Forms/LoginForm';
import Button from '../Buttons/CommonButton';
import styles from './Header.module.css';
import { Context } from '../../main';

const Header = observer(() => {
    const { store } = useContext(Context);
    const [isOpenModal, setIsModalOpen] = React.useState(false);

    useEffect(() => {
        console.log('isAuth змінився:', store.isAuth);
    }, [store.isAuth]);

    return (
        <header className={styles.header}>
            <div className={styles.header__container}>
                <div className="logo">
                    <Link to={'/'}>
                        <img src="logo.svg" alt="logo" />
                    </Link>
                </div>
                <div>
                    {store.isAuth ? (
                        <Button onClick={() => store.logout()}>Logout</Button>
                    ) : (
                        <Button onClick={() => setIsModalOpen(true)}>Login</Button>
                    )}
                    <Navigation />
                </div>
            </div>
            <Modal active={isOpenModal} setActive={setIsModalOpen}>
                <LoginForm setActive={setIsModalOpen}></LoginForm>
            </Modal>
        </header>
    );
});

export default Header;