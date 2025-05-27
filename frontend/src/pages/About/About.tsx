import styles from "./About.module.css";

const About = () => {
    return (
        <section className={styles.aboutSection}>
            <div className={styles.aboutContainer}>
                <div className={styles.aboutText}>
                    <h1>About GymPoint</h1>
                    <p>
                        <b>GymPoint</b> is a modern platform for managing workouts, memberships, and communication between coaches and clients. Our goal is to make fitness accessible, efficient, and convenient for everyone!
                    </p>
                    <h3>Our Mission</h3>
                    <p>
                        To help people achieve their fitness goals through technology and a strong community.
                    </p>
                    <ul className={styles.benefits}>
                        <li>✅ Easy scheduling and class booking</li>
                        <li>✅ Direct chat with coaches</li>
                        <li>✅ Online store for fitness products</li>
                        <li>✅ Personalized training programs</li>
                    </ul>
                    <div className={styles.contactBlock}>
                        <h3>Contact Us</h3>
                        <p>Email: <a href="mailto:support@gympoint.com">support@gympoint.com</a></p>
                        <p>Phone: +1 (234) 567-890</p>
                        <p>We are open to collaboration and your ideas!</p>
                    </div>
                </div>
                <div className={styles.aboutImage}>
                    <img src="/about-us-gym.jpeg" alt="Gym illustration" />
                </div>
            </div>
        </section>
    );
};

export default About;
