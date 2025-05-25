import Hero from './component/HeroBlock/Hero'
import About from './component/About/AboutBlock';
import Advantage from './component/Advantage/Advantage';

const Home = () => {
    return (<>
        <Hero title={<p>Welcome to <br /> <span>Gympoint</span></p>} description={<p>Your Fitness Journey<br /> Starts Here</p>} />
        <About />
        <Advantage />
    </>)
}


export default Home;