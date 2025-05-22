import React from "react"
import styles from "./About.module.css";
const AboutBlock = () => {
  return (
    <div className={styles.block}>
      <div className={styles.__container}>
        <div className={styles.content}>
          <h3>Take GymPoint With You</h3>
          <p className={styles.text}>Your fitness journey doesn't stop at the gym. With our mobile app, you can stay connected, motivated, and on track — wherever you are.</p>
          <ul>
            <p className={styles.ulText}>✅ What you can do in the app:</p>
            <li>Book and manage your workouts</li>
            <li>Chat with your personal trainer</li>
            <li>Track your fitness progress</li>
            <li>Renew memberships instantly</li>
            <li>Receive real-time updates and reminders</li>
          </ul>
        </div>
         <div className={styles.photo}>
          <img src="/telephone.png" alt="telephone" />
        </div>
      </div>
    </div>
  )
};

export default AboutBlock;
