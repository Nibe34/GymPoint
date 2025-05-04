import React from 'react';

import * as styles from "./app.module.scss";

const App = () => {
    console.log('Styles:', styles);
  return <div className={styles.className}>Hello, React!</div>;
};

export default App;