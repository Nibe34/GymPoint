import React from "react"
import { NavLink } from "react-router-dom";

const name = () => {
  return (
    <ul>
      <li><NavLink to='/about-us'>About us</NavLink></li>
    </ul>
  )
};

export default name;
