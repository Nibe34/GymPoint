import React, { useContext } from 'react';
import { Form, Input, Button } from 'antd';
import type ITrainer from '../../../Models/users/ITrainer';
import { Context } from '../../../main';
import Roles from '../../../Models/users/Roles';

const TrainerForm: React.FC = () => {
  const [form] = Form.useForm<ITrainer & { password: string; confirmPassword: string }>();

  const { store } = useContext(Context);

  const handleSubmit = (values: any) => {
    const { confirmPassword, ...rest } = values;
    console.log('Submitted trainer data without confirmPassword:', rest);
    store.registration(rest);


  };

  return (
    <Form className='__container'
      layout="vertical"
      form={form}
      onFinish={handleSubmit}
      initialValues={{ role: Roles.trainer }}
    >
      <Form.Item
        label="First Name"
        name="firstName"
        rules={[{ required: true, message: 'Please enter first name' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Last Name"
        name="lastName"
        rules={[{ required: true, message: 'Please enter last name' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Email"
        name="email"
        rules={[{ required: true, type: 'email', message: 'Enter valid email' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Password"
        name="password"
        rules={[
          { required: true, message: 'Please enter a password' },
          { min: 6, message: 'Password must be at least 6 characters' },
        ]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        label="Confirm Password"
        name="confirmPassword"
        dependencies={['password']}
        hasFeedback
        rules={[
          { required: true, message: 'Please confirm your password' },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('password') === value) {
                return Promise.resolve();
              }
              return Promise.reject(new Error('Passwords do not match!'));
            },
          }),
        ]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        label="Specialization"
        name="specialization"
        rules={[{ required: true, message: 'Enter specialization' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Certification"
        name="certification"
        rules={[{ required: true, message: 'Enter certification info' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Bio"
        name="bio"
        rules={[{ required: true, message: 'Enter a short bio' }]}
      >
        <Input.TextArea rows={4} />
      </Form.Item>

      <Form.Item name="role" hidden>
        <Input value={Roles.trainer} />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Submit Trainer
        </Button>
      </Form.Item>
    </Form>
  );
};

export default TrainerForm;
