// pages/MePage.tsx
import React, { useEffect, useState } from "react";
import { Card, Spin, Alert, Descriptions, Button, message } from "antd";
import UserService from "../../../services/UserService";
import type { UserType } from "../../../Models/users/UserType";
import Roles from "../../../Models/users/Roles";
import EditProfileModal from "./component/EditProfileModal";
import TrainerService from "../../../services/TrainerService";
import ClientService from "../../../services/ClientService";

const MyProfile = () => {
    const [user, setUser] = useState<UserType | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string>("");
    const [isModalVisible, setIsModalVisible] = useState<boolean>(false);

    useEffect(() => {
        fetchUser();
    }, []);

    const fetchUser = () => {
        setLoading(true);
        UserService.fetchCurrentUser()
            .then((response) => {
                setUser(response.data);
            })
            .catch(() => {
                setError("Не вдалося завантажити профіль.");
            })
            .finally(() => setLoading(false));
    };

    const handleSave = async (updatedFields: Partial<UserType>) => {
        if (!user) return;

        try {
            if (user.role === Roles.trainer) {
                await TrainerService.updateTrainer(user.id, updatedFields);
            } else if (user.role === Roles.client) {
                await ClientService.updateClient(user.id, updatedFields);
            }

            message.success("Профіль оновлено успішно!");
            setIsModalVisible(false);
            fetchUser();
        } catch (err) {
            message.error("Помилка при оновленні профілю.");
        }
    };

    if (error) return <Alert message="Помилка" description={error} type="error" showIcon />;

    return (
        <Spin spinning={loading} tip="Завантаження профілю..." style={{ marginTop: '20px' }}>
            {user && (
                <Card
                    title="Мій профіль"
                    style={{ maxWidth: 700, margin: "0 auto" }}
                    extra={
                        user.role !== Roles.admin && (
                            <Button type="primary" onClick={() => setIsModalVisible(true)}>
                                Редагувати
                            </Button>
                        )
                    }
                >
                    <Descriptions column={1} bordered>
                        <Descriptions.Item label="Ім'я">{user.firstName}</Descriptions.Item>
                        <Descriptions.Item label="Прізвище">{user.lastName}</Descriptions.Item>
                        <Descriptions.Item label="Email">{user.email}</Descriptions.Item>
                        <Descriptions.Item label="Роль">{user.role}</Descriptions.Item>

                        {user.role === Roles.trainer && (
                            <>
                                <Descriptions.Item label="Біографія">{user.bio}</Descriptions.Item>
                                <Descriptions.Item label="Спеціалізація">{user.specialization}</Descriptions.Item>
                                <Descriptions.Item label="Сертифікація">{user.certification}</Descriptions.Item>
                            </>
                        )}

                        {user.role === Roles.client && (
                            <>
                                <Descriptions.Item label="Дата народження">
                                    {user.dateOfBirth?.slice(0, 10)}
                                </Descriptions.Item>
                                <Descriptions.Item label="Телефон">{user.phoneNumber}</Descriptions.Item>
                                <Descriptions.Item label="Адреса">{user.address}</Descriptions.Item>
                                <Descriptions.Item label="Контакт екстреного зв'язку">{user.emergencyContact}</Descriptions.Item>
                            </>
                        )}
                    </Descriptions>
                </Card>
            )}

            {user && (
                <EditProfileModal
                    visible={isModalVisible}
                    user={user}
                    onSave={handleSave}
                    onCancel={() => setIsModalVisible(false)}
                />
            )}
        </Spin>
    );
};

export default MyProfile;
