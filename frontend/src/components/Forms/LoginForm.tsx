 import React, {useState} from 'react';
 import type {FC} from 'react';
 import CommonButton from '../Buttons/CommonButton';

 import styles from './LoginForm.module.css'
 
 const LoginForm: FC = () => { 
    const [email, setEmail] = useState<string>('')
    const [password, setPassword] = useState<string>('')

    return (
        <div className={styles.form}>
            <input
                onChange={e => setEmail(e.target.value)}
                value={email}
                type='email'
                placeholder='Email'
            />
             <input
                onChange={e => setPassword(e.target.value)}
                value={password}
                type='password'
                placeholder='Password'
            />
            <CommonButton>Login</CommonButton>
        </div>
    )
 }


 export default LoginForm;