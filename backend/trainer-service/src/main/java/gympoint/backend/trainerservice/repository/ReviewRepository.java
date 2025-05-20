package gympoint.backend.trainerservice.repository;

import gympoint.backend.trainerservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTrainerId(Long trainerId);
    List<Review> findByClientId(Long clientId);
    boolean existsByTrainerIdAndClientId(Long trainerId, Long clientId);
} 