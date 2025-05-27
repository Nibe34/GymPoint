import React, { useContext, useState } from 'react';
import type { FormProps } from 'antd';
import { Button, Form, Input, Alert } from 'antd';
import { NavLink, useNavigate } from 'react-router-dom';
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
  const navigate = useNavigate();
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  const onFinish: FormProps<FieldType>['onFinish'] = async (values) => {
    setErrorMsg(null); // Скидаємо попередню помилку перед новим логіном
    try {
      const response = await store.login(values.email, values.password);
      if (response && response.status === 200) {
        setActive(false);
        navigate("/profile");
      }
    } catch (error) {
      setErrorMsg("Невірний email або пароль.");
      // Можна логувати детальніше
      console.error('Помилка входу:', error);
    }
  };

  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    setErrorMsg(null); // При валідації не показуємо помилку логіну
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

      {/* Додаємо показ помилки якщо є */}
      {errorMsg && (
        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Alert message={errorMsg} type="error" showIcon />
        </Form.Item>
      )}

      <Form.Item label={null}>
        <Button type="primary" htmlType="submit">
          Login
        </Button>
      </Form.Item>

      <NavLink to={"/registration"} className={styles.registration} onClick={() => setActive(false)}>
        Don't have an account? Register
      </NavLink>
    </Form>
  )
};

export default LoginForm;
