import React, { useContext } from 'react';
import { Form, Input, Button, DatePicker } from 'antd';
import { Context } from '../../main';
import type IClient from '../../Models/users/IClient';
import Roles from '../../Models/users/Roles';
import dayjs from 'dayjs';

const ClientForm: React.FC = () => {
  const [form] = Form.useForm<IClient & { password: string; confirmPassword: string }>();
  const { store } = useContext(Context);

  const handleSubmit = (values: any) => {
    const { confirmPassword, ...rest } = values;
    const formatted = {
      ...rest,
      dateOfBirth: values.dateOfBirth.format('YYYY-MM-DD'), // форматування дати
    };
    console.log('Submitted client data:', formatted);
    store.registration(formatted);
  };

  return (
    <Form
      layout="vertical"
      form={form}
      onFinish={handleSubmit}
      initialValues={{ role: Roles.client }}
    >
      <Form.Item label="First Name" name="firstName" rules={[{ required: true }]}>
        <Input />
      </Form.Item>

      <Form.Item label="Last Name" name="lastName" rules={[{ required: true }]}>
        <Input />
      </Form.Item>

      <Form.Item label="Email" name="email" rules={[{ required: true, type: 'email' }]}>
        <Input />
      </Form.Item>

      <Form.Item label="Password" name="password" rules={[{ required: true, min: 6 }]} hasFeedback>
        <Input.Password />
      </Form.Item>

      <Form.Item
        label="Confirm Password"
        name="confirmPassword"
        dependencies={['password']}
        hasFeedback
        rules={[
          { required: true },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('password') === value) return Promise.resolve();
              return Promise.reject(new Error('Passwords do not match!'));
            },
          }),
        ]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item label="Date of Birth" name="dateOfBirth" rules={[{ required: true }]}>
        <DatePicker style={{ width: '100%' }} />
      </Form.Item>

      <Form.Item label="Phone Number" name="phoneNumber" rules={[{ required: true }]}>
        <Input />
      </Form.Item>

      <Form.Item label="Address" name="address" rules={[{ required: true }]}>
        <Input />
      </Form.Item>

      <Form.Item label="Emergency Contact" name="emergencyContact" rules={[{ required: true }]}>
        <Input />
      </Form.Item>

      <Form.Item name="role" hidden>
        <Input value={Roles.client} />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Submit Client
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ClientForm;
