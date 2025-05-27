// components/Nav.tsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import { navItems } from '../../Models/NavItems';
import styles from './Navigation.module.css';

const Nav: React.FC = () => {
  return (
    <nav>
      <ul className={styles.nav}>
        {navItems.map((item) => (
          <li key={item.path} className={styles.li}>
            <NavLink
              to={item.path}
              className={styles.link}
            >
              {item.label}
            </NavLink>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Nav;