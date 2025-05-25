import React, { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { observer } from 'mobx-react-lite';
import Navigation from '../Navigation/Navigation';
import Modal from '../Modal/Modal';
import LoginForm from '../Forms/LoginForm';
import Button from '../Buttons/CommonButton';
import { Drawer, Button as AntButton } from 'antd';
import { MenuOutlined } from '@ant-design/icons';
import { Context } from '../../main';
import styles from './Header.module.css';

const Header = observer(() => {
    const { store } = useContext(Context);
    const [isOpenModal, setIsModalOpen] = useState(false);
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    useEffect(() => {
        console.log('isAuth змінився:', store.isAuth);
    }, [store.isAuth]);

    const handleLoginClick = () => {
        setIsDrawerOpen(false);
        setIsModalOpen(true);
    };

    return (
        <header className={styles.header}>
            <div className={styles.header__container}>
                <div className={styles.logo}>
                    <Link to="/">
                        <img src="logo.svg" alt="logo" />
                    </Link>
                </div>

                <div className={styles.desktopMenu}>
                    {store.isAuth ? (
                        <Button onClick={() => store.logout()}>Logout</Button>
                    ) : (
                        <Button onClick={() => setIsModalOpen(true)}>Login</Button>
                    )}
                    <Navigation />
                </div>

                <div className={styles.burgerMenu}>
                    <AntButton
                        type="text"
                        icon={<MenuOutlined />}
                        onClick={() => setIsDrawerOpen(true)}
                    />
                </div>
            </div>

            <Drawer
                title="Menu"
                placement="left"
                onClose={() => setIsDrawerOpen(false)}
                open={isDrawerOpen}
                width={250}
            >
                <div className={styles.drawerContent}>
                    <Navigation />
                    {store.isAuth ? (
                        <Button onClick={() => {
                            store.logout();
                            setIsDrawerOpen(false);
                        }}>Logout</Button>
                    ) : (
                        <Button onClick={handleLoginClick}>Login</Button>
                    )}
                </div>
            </Drawer>

            <Modal active={isOpenModal} setActive={setIsModalOpen}>
                <LoginForm setActive={setIsModalOpen} />
            </Modal>
        </header>
    );
});

export default Header;
