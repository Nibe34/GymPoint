import Hero from '../components/HeroBlock/Hero'
import About from '../components/About/AboutBlock';
import Advantage from '../components/Advantage/Advantage';

const Home = () => { 
    return (<>  
    <Hero title={<p>Welcome to <br/> <span>Gympoint</span></p>} description= {<p>Your Fitness Journey<br/> Starts Here</p>}/>
    <About/>
    <Advantage/>
   </>)
}


export default Home;