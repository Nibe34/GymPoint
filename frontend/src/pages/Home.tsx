import * as React from 'react';
import Hero from '../components/HeroBlock/Hero'

const Home = () => { 
    return (<>  
    <Hero title={<p>Welcome to <br/> <span>Gympoint</span></p>} description= {<p>Your Fitness Journey<br/> Starts Here</p>}/>
   </>)
}


export default Home;