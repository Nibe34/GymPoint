import React, { useEffect, useState } from "react";
import TrainerService from "../../services/TrainerService";
import type ITrainer from "../../Models/users/ITrainer";
import styles from "./CoachesPage.module.css";

const CoachesPage: React.FC = () => {
    const [trainers, setTrainers] = useState<ITrainer[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        TrainerService.fetchTrainers()
            .then(response => setTrainers(response.data))
            .catch(() => setError("Не вдалося отримати тренерів"))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div>Завантаження...</div>;
    if (error) return <div style={{ color: "red" }}>{error}</div>;

    return (
        <div className={styles.coachGrid}>
            {trainers.map(trainer => (
                <div className={styles.coachCard} key={trainer.id}>
                    <div className={styles.avatar}>
                        {/* Можна додати аватар якщо буде */}
                        <img src="/trainer-default.png" alt="avatar" />
                    </div>
                    <div className={styles.info}>
                        <h3>{trainer.firstName} {trainer.lastName}</h3>
                        <p><strong>Спеціалізація:</strong> {trainer.specialization}</p>
                        <p><strong>Сертифікація:</strong> {trainer.certification}</p>
                        <p>{trainer.bio}</p>
                        <p className={styles.email}>{trainer.email}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default CoachesPage;
