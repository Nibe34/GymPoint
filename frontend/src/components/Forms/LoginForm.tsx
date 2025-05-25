import React, { useContext } from 'react';
import type { FormProps } from 'antd';
import { Button, Form, Input } from 'antd';
import { NavLink } from 'react-router-dom';
import styles from './LoginForm.module.css'
import { Context } from '../../main';

type FieldType = {
  email: string;
  password: string;
};


interface ModalProps {
  setActive: (value: boolean) => void;
}



const LoginForm = ({ setActive }: ModalProps) => {

  const { store } = useContext(Context);

  const onFinish: FormProps<FieldType>['onFinish'] = async (values) => {
    try {
      console.log('Success:', values);
      await store.login(values.email, values.password); // Чекаємо завершення логіну
      setActive(false); // Закриваємо модалку після успішного входу
    } catch (error) {
      console.error('Помилка входу:', error); // Логуємо помилку, якщо логін не вдався
    }
  };

  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  return (
    <Form
      name="basic"
      labelCol={{ span: 8 }}
      wrapperCol={{ span: 16 }}
      style={{ maxWidth: 600 }}
      initialValues={{ remember: true }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item<FieldType>
        label="Email"
        name="email"
        rules={[{ required: true, message: 'Please input your email!' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item<FieldType>
        label="Password"
        name="password"
        rules={[{ required: true, message: 'Please input your password!' }]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item label={null}>
        <Button type="primary" htmlType="submit" onSubmit={() => setActive(false)}>
          Login
        </Button>
      </Form.Item>
      <NavLink to={"/registration"} className={styles.registration} onClick={() => setActive(false)}>Don't have an account? Register</NavLink>
    </Form>
  )
};

export default LoginForm;