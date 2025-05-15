import * as React from 'react';
import Hero from '../components/HeroBlock/Hero'

const Home = () => { 
    return (<>  
    <Hero title={<p>Welcome to <span>Gympoint</span></p>} description= {<p>Your Fitness Journey Starts Here</p>} imageSrc='/hero2.png' imageAlt='Hero at Gym'/>
   </>)
}


export default Home;