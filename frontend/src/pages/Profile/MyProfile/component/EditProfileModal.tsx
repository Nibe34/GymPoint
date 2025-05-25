// components/EditProfileModal.tsx
import React, { useEffect } from "react";
import { Modal, Form, Input, DatePicker } from "antd";
import dayjs from "dayjs";
import type { UserType } from "../../../../Models/users/UserType";
import Roles from "../../../../Models/users/Roles";

interface Props {
    visible: boolean;
    user: UserType;
    onSave: (updatedUser: Partial<UserType>) => void;
    onCancel: () => void;
}

const EditProfileModal: React.FC<Props> = ({ visible, user, onSave, onCancel }) => {
    const [form] = Form.useForm();

    useEffect(() => {
        if (user) {
            const initialData: any = { ...user };
            if (user.role === Roles.client && user.dateOfBirth) {
                initialData.dateOfBirth = dayjs(user.dateOfBirth);
            }
            form.setFieldsValue(initialData);
        }
    }, [user, form]);

    const handleSubmit = () => {
        form
            .validateFields()
            .then((values) => {
                if (values.dateOfBirth) {
                    values.dateOfBirth = values.dateOfBirth.toISOString();
                }
                onSave(values);
                form.resetFields();
            })
            .catch((info) => {
                console.log("Validation failed:", info);
            });
    };

    return (
        <Modal
            title="Редагувати профіль"
            open={visible}
            onOk={handleSubmit}
            onCancel={onCancel}
            okText="Зберегти"
            cancelText="Скасувати"
        >
            <Form layout="vertical" form={form}>
                <Form.Item name="firstName" label="Ім'я" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="lastName" label="Прізвище" rules={[{ required: true }]}>
                    <Input />
                </Form.Item>
                <Form.Item name="email" label="Email" rules={[{ required: true, type: "email" }]}>
                    <Input disabled />
                </Form.Item>

                {user.role === Roles.trainer && (
                    <>
                        <Form.Item name="bio" label="Біографія">
                            <Input.TextArea />
                        </Form.Item>
                        <Form.Item name="specialization" label="Спеціалізація">
                            <Input />
                        </Form.Item>
                        <Form.Item name="certification" label="Сертифікація">
                            <Input />
                        </Form.Item>
                    </>
                )}

                {user.role === Roles.client && (
                    <>
                        <Form.Item name="dateOfBirth" label="Дата народження">
                            <DatePicker format="YYYY-MM-DD" />
                        </Form.Item>
                        <Form.Item name="phoneNumber" label="Телефон">
                            <Input />
                        </Form.Item>
                        <Form.Item name="address" label="Адреса">
                            <Input />
                        </Form.Item>
                        <Form.Item name="emergencyContact" label="Контакт екстреного зв'язку">
                            <Input />
                        </Form.Item>
                    </>
                )}
            </Form>
        </Modal>
    );
};

export default EditProfileModal;
