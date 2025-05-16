import * as React from 'react';
import styles from "./CommonButton.module.css";

interface CommonButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
}

const CommonButton = ({children, onClick}: CommonButtonProps) => {
    return (<button className= {styles.button} onClick={onClick}>{children}</button>)
}

export default CommonButton;