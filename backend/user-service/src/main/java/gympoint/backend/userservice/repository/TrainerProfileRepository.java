package gympoint.backend.userservice.repository;


import gympoint.backend.userservice.entity.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {
}
