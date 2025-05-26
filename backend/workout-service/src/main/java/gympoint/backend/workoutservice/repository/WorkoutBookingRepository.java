package gympoint.backend.workoutservice.repository;

import gympoint.backend.workoutservice.entity.WorkoutBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutBookingRepository extends JpaRepository<WorkoutBooking, Long> {
    List<WorkoutBooking> findByClientId(Long clientId);
    List<WorkoutBooking> findByTrainerId(Long trainerId);
    List<WorkoutBooking> findByTrainingSessionId(Long trainingSessionId);
} 