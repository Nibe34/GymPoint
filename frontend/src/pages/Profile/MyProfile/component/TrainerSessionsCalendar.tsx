import React, { useEffect, useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import type { View } from "react-big-calendar";
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Spin, Alert, Modal, InputNumber, Button } from "antd";
import type { TrainingSessionDto } from "../../../../services/TrainingSessionService";
import TrainingSessionService from "../../../../services/TrainingSessionService";
import WorkoutBookingService from "../../../../services/WorkoutBookingService";
import { format, parseISO, differenceInHours } from "date-fns";
import moment from "moment";
import type { SlotInfo } from 'react-big-calendar';

const calendarStyle = {
    height: 600,
    margin: "40px auto",
    maxWidth: 900,
    background: "white",
    borderRadius: 16,
    padding: 16,
    position: 'relative'
};

const localizer = momentLocalizer(moment);
interface TrainerSessionsCalendarProps {
    trainerId: number;
    readonly?: boolean;
    clientId?: number;
}

const TrainerSessionsCalendar: React.FC<TrainerSessionsCalendarProps> = ({
    trainerId,
    readonly = false,
    clientId
}) => {
    const [sessions, setSessions] = useState<TrainingSessionDto[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [selectedEvent, setSelectedEvent] = useState<any>(null);
    const [view, setView] = useState<View>("month");
    const [date, setDate] = useState<Date>(new Date());

    // Для повідомлень запису
    const [bookingLoading, setBookingLoading] = useState(false);
    const [bookingSuccess, setBookingSuccess] = useState<string | null>(null);
    const [bookingError, setBookingError] = useState<string | null>(null);

    // Для бронювань
    const [bookings, setBookings] = useState<any[]>([]);
    const [bookingsLoading, setBookingsLoading] = useState(false);

    const fetchSessions = () => {
        setLoading(true);
        TrainingSessionService.fetchByTrainer(trainerId)
            .then(res => setSessions(res.data))
            .catch(() => setError("Не вдалося отримати сесії"))
            .finally(() => setLoading(false));
    };

    useEffect(() => {
        fetchSessions();
    }, [trainerId]);

    // Оновлюємо список бронювань при відкритті модалки
    useEffect(() => {
        if (selectedEvent && selectedEvent.id) {
            setBookingsLoading(true);
            WorkoutBookingService.fetchBookingsBySession(selectedEvent.id)
                .then(res => setBookings(res.data))
                .catch(() => setBookings([]))
                .finally(() => setBookingsLoading(false));
        } else {
            setBookings([]);
        }
    }, [selectedEvent]);

    const handleSelectSlot = (slotInfo: SlotInfo) => {
        if (readonly) return;
        const now = new Date();
        const hour = slotInfo.start.getHours();

        if (slotInfo.start < now) {
            Modal.warning({
                title: "Виберіть майбутню дату",
                content: "Неможливо створити тренування у минулому.",
            });
            return;
        }
        if (view === "month") {
            setView('week');
            setDate(slotInfo.start);
        } else if (view === "week" || view === "day") {
            if (hour < 8 || hour >= 20) return;

            const maxRef = { current: 10 };

            const Content = () => (
                <div>
                    <div>Час: {format(slotInfo.start, 'dd.MM.yyyy HH:mm')}</div>
                    <div style={{ marginTop: 12 }}>
                        <span>Кількість місць: </span>
                        <InputNumber
                            min={1}
                            max={100}
                            defaultValue={10}
                            onChange={v => { maxRef.current = Number(v); }}
                        />
                    </div>
                </div>
            );

            Modal.confirm({
                title: "Створити тренування?",
                content: <Content />,
                onOk: () => {
                    return TrainingSessionService.createSession({
                        trainerId,
                        startTime: moment(slotInfo.start).format("YYYY-MM-DDTHH:mm:00"),
                        endTime: moment(slotInfo.end).format("YYYY-MM-DDTHH:mm:00"),
                        maxParticipants: maxRef.current,
                        isAvailable: true,
                    }).then(() => {
                        fetchSessions();
                    });
                }
            });
        }
    };

    const events = sessions
        .filter(s => s.startTime)
        .map((s) => {
            const start = parseISO(s.startTime);
            const end = s.endTime ? parseISO(s.endTime) : new Date(start.getTime() + 60 * 60 * 1000);
            return {
                id: s.id,
                title: s.isAvailable ? "Available" : "open",
                start,
                end,
                isAvailable: s.isAvailable,
                maxParticipants: s.maxParticipants,
            }
        });

    // Видалення сесії
    const handleDeleteSession = async () => {
        if (!selectedEvent) return;
        await TrainingSessionService.deleteSession(selectedEvent.id);
        setSelectedEvent(null);
        fetchSessions();
    };

    // Чи можна видаляти (48 год)
    let canDelete = true;
    if (selectedEvent) {
        const hoursDiff = differenceInHours(selectedEvent.start, new Date());
        canDelete = hoursDiff >= 48;
    }

    // Записатись на тренування
    const handleBookSession = async () => {
        if (!selectedEvent) return;
        setBookingLoading(true);
        setBookingSuccess(null);
        setBookingError(null);
        try {
            await WorkoutBookingService.createBooking({
                trainingSessionId: selectedEvent.id,
                clientId: clientId ?? 1, // <-- підставити user.id з контексту
                trainerId: trainerId,
            });
            setBookingSuccess("Ви успішно записались на тренування!");
        } catch (e: any) {
            setBookingError("Не вдалося записатись. Можливо, місць вже немає.");
        }
        setBookingLoading(false);
    };

    // ---- Loading та error ----
    if (loading) {
        return (
            <div style={calendarStyle}>
                <Spin
                    size="large"
                    style={{
                        position: "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)"
                    }}
                />
            </div>
        );
    }
    if (error) return <Alert message={error} type="error" style={{ maxWidth: 900, margin: "40px auto" }} />;

    // ---- FOOTER для модалки ----
    const modalFooter = (
        <>
            {!readonly && selectedEvent && (
                <Button
                    danger
                    disabled={!canDelete}
                    onClick={handleDeleteSession}
                >
                    Видалити тренування
                </Button>
            )}
            {readonly && selectedEvent && selectedEvent && (
                <Button
                    type="primary"
                    loading={bookingLoading}
                    onClick={handleBookSession}
                >
                    Записатись
                </Button>
            )}
        </>
    );

    return (
        <>
            <div style={calendarStyle}>
                <Calendar
                    localizer={localizer}
                    events={events}
                    selectable={!readonly}
                    date={date}
                    onNavigate={setDate}
                    view={view}
                    onView={setView}
                    onSelectSlot={handleSelectSlot}
                    min={new Date(1970, 1, 1, 8, 0)}
                    max={new Date(1970, 1, 1, 20, 0)}
                    style={{
                        height: 600,
                        background: "white",
                        borderRadius: 16,
                        padding: 16
                    }}
                    eventPropGetter={event => ({
                        style: {
                            backgroundColor: '#e0ffe0',
                            color: 'black',
                            borderRadius: 8,
                            border: '1px solid #56b856',
                        }
                    })}
                    onSelectEvent={event => {
                        setSelectedEvent(event);
                        setBookingSuccess(null);
                        setBookingError(null);
                    }}
                    messages={{
                        next: "Next",
                        previous: "Prev",
                        today: "Today",
                        month: "Month",
                        week: "Week",
                        day: "Day",
                    }}
                />
            </div>
            <Modal
                open={!!selectedEvent}
                onCancel={() => {
                    setSelectedEvent(null);
                    setBookingSuccess(null);
                    setBookingError(null);
                }}
                title="Training Session Details"
                footer={modalFooter}
            >
                {selectedEvent && (
                    <div>
                        <b>Status:</b> {selectedEvent.isAvailable ? "Available" : "open"}<br />
                        <b>Time:</b> {format(selectedEvent.start, 'dd.MM.yyyy HH:mm')} — {format(selectedEvent.end, 'HH:mm')}<br />
                        <b>Session ID:</b> {selectedEvent.id}<br />
                        <b>Максимальна кількість учасників:</b> {selectedEvent.maxParticipants ?? '—'}<br />
                        {/* --- Показати список зареєстрованих користувачів тренеру --- */}
                        {!readonly && (
                            <>
                                <hr style={{ margin: "12px 0" }} />
                                <b>Записані користувачі:</b>
                                {bookingsLoading ? (
                                    <div>Завантаження...</div>
                                ) : bookings.length === 0 ? (
                                    <div>Ніхто ще не записався</div>
                                ) : (
                                    <ul>
                                        {bookings.map((booking) => (
                                            <li key={booking.id}>
                                                {booking.clientFullName || `ID: ${booking.clientId}`}
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </>
                        )}

                        {!readonly && !canDelete && (
                            <div style={{ color: 'red', marginTop: 12 }}>
                                Подію можна видалити не пізніше, ніж за 48 годин до початку!
                            </div>
                        )}
                        {/* Повідомлення про запис */}
                        {bookingSuccess && (
                            <div style={{ color: "green", marginTop: 8 }}>{bookingSuccess}</div>
                        )}
                        {bookingError && (
                            <div style={{ color: "red", marginTop: 8 }}>{bookingError}</div>
                        )}
                    </div>
                )}
            </Modal>
        </>
    );
};

export default TrainerSessionsCalendar;
