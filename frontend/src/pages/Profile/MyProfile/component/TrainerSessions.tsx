import React, { useEffect, useState } from "react";
import type { TrainingSessionDto } from "../../../../services/TrainingSessionService";
import TrainingSessionService from "../../../../services/TrainingSessionService";
import { Alert, Spin } from "antd";

const TrainerSessions = ({ trainerId }: { trainerId: number }) => {
    const [sessions, setSessions] = useState<TrainingSessionDto[]>([]);
    const [sessionsLoading, setSessionsLoading] = useState(false);
    const [sessionsError, setSessionsError] = useState<string | null>(null);

    useEffect(() => {
        if (trainerId) {
            setSessionsLoading(true);
            TrainingSessionService.fetchByTrainer(trainerId)
                .then(res => setSessions(res.data))
                .catch(() => setSessionsError("Не вдалося отримати сесії"))
                .finally(() => setSessionsLoading(false));
        }
    }, [trainerId]);

    if (sessionsLoading) return <Spin />;
    if (sessionsError) return <Alert message={sessionsError} type="error" />;

    return (
        <div style={{ marginTop: 30 }}>
            <h3>Training Sessions</h3>
            <ul>
                {sessions.map(s => (
                    <li key={s.id}>
                        <b>{s.startTime.slice(0, 16)} — {s.endTime.slice(0, 16)}</b>
                        {" "}
                        {s.isAvailable
                            ? <span style={{ color: 'green' }}>Available</span>
                            : <span style={{ color: 'red' }}>Booked</span>
                        }
                    </li>
                ))}
                {sessions.length === 0 && <li>No sessions yet.</li>}
            </ul>
        </div>
    );
};

export default TrainerSessions;
