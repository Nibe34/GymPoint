import React from "react"
import styles from './Advantage.module.css'
import { Link } from "react-router-dom"

const Advantage = () => {
  return (
    <div className={styles.__container}>
        <Link to={"/coaches"} className={styles.gridBlock}><div><p>Personal <br /> training</p></div></Link>
        <Link to={"/coaches"} className={styles.gridBlock}><div><p>Group <br /> training</p></div></Link>
        <Link to={"/coaches"} className={styles.gridBlock}><div><p>communion  <br />with trainer</p></div></Link>
        <Link to={"/coaches"} className={styles.gridBlock}><div><p>Online <br /> training</p></div></Link>
        <Link to={"/shop"} className={styles.gridBlock}><div><p>Online <br /> store</p></div></Link>
    </div>
  )
};

export default Advantage;
