import * as React from 'react';
import styles from "./CommonButton.module.css";

interface CommonButtonProps {
  children: React.ReactNode;
}

const CommonButton = ({children}: CommonButtonProps) => {
    return (<button className= {styles.button}>{children}</button>)
}

export default CommonButton;