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
        <div onClick={(e) => e.stopPropagation()}>
            {children}
        </div>
    </div>)
}

export default Modal;