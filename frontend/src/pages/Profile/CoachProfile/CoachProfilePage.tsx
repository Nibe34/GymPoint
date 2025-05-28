import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import TrainerService from "../../../services/TrainerService";
import type ITrainer from "../../../Models/users/ITrainer";
import TrainerSessionsCalendar from "../MyProfile/component/TrainerSessionsCalendar";
import styles from "./CoachProfilePage.module.css";

const CoachProfilePage: React.FC = () => {
    const { id } = useParams();
    const [trainer, setTrainer] = useState<ITrainer | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        TrainerService.fetchTrainerById(id!)
            .then((res) => setTrainer(res.data))
            .catch(() => setError("Не вдалося отримати профіль тренера"))
            .finally(() => setLoading(false));
    }, [id]);

    if (loading) return <div>Завантаження...</div>;
    if (error) return <div style={{ color: "red" }}>{error}</div>;
    if (!trainer) return <div>Тренер не знайдений</div>;

    return (
        <div className={styles.profileWrapper}>
            <div className={styles.coachHeader}>
                <img src="/trainer-default.svg" alt="avatar" className={styles.avatar} />
                <div>
                    <h2>{trainer.firstName} {trainer.lastName}</h2>
                    <div><b>Спеціалізація:</b> {trainer.specialization}</div>
                    <div><b>Сертифікація:</b> {trainer.certification}</div>
                    <div>{trainer.bio}</div>
                </div>
            </div>
            <div style={{ marginTop: 40 }}>
                <h3>Календар доступних тренувань:</h3>
                <TrainerSessionsCalendar trainerId={Number(id)} readonly />

            </div>
        </div>
    );
};

export default CoachProfilePage;
