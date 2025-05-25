import React from 'react';
import { useNavigate } from 'react-router-dom';
import CommonButton from '../../../../components/Buttons/CommonButton';
import styles from './Hero.module.css';

interface HeroProps {
  title: React.ReactNode;
  description: React.ReactNode;
  to?: string;
}

const Hero = ({ title, description, to = '/coaches' }: HeroProps) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(to);
  };

  return (
    <section className={styles.hero}>
      <div className={styles.hero__container}>
        <div className={styles.content}>
          <div className={styles.title}>{title}</div>
          <div className={styles.description}>{description}</div>
          <div className={styles.buttonContainer}>
            <CommonButton onClick={handleClick}>Get Started</CommonButton>
          </div>
        </div>
      </div>
    </section>
  );
};


export default Hero;