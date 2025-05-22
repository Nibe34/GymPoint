import React from 'react';

import styles from './Modal.module.css'

interface ModalProps {
    active: boolean,
    setActive: (value: boolean) => void;
    children: React.ReactNode;
}

const Modal = ({active, setActive, children}: ModalProps) => {
    return(
    <div onClick={() => setActive(false)} className={`${styles.modal} ${active ? styles.active : ''}`}>
        <div onClick={(e) => e.stopPropagation()} className={styles.content}>
            <div><img src="/close.svg" alt="close" onClick={() => setActive(false)} className={styles.close}/></div>
            {children}
        </div>
    </div>)
}

export default Modal;