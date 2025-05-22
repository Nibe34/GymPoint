import React from "react"
import Navigation from "../Navigation/Navigation";
import Support from "../SupportLinks/Support"
import styles from './Footer.module.css'
const Footer = () => {
  return (
    <footer>
      <div className={styles.__container}>
        <div className={styles.logo}>
            <h3>GymPoint</h3>
            <p>Smart gym management system for bookings,
                memberships, trainers, and more.</p>
        </div>
        <div className={styles.block}>
            <h3>Quick Links</h3>
            <Navigation/>
        </div>
        <div className={styles.block}>
            <h3>Support</h3>
            <Support/>
        </div>
         <div className={styles.block}>
            <h3>Contact</h3>
            <ul>
                <li>support@gympoint.com</li>
                <li>+1 (234) 567-890</li>
            </ul>
        </div>
      </div>
      <div className={styles.bottom}>© 2025 GymPoint</div>
    </footer>
  )
};

export default Footer;
