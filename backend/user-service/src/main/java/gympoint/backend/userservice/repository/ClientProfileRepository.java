package gympoint.backend.userservice.repository;


import gympoint.backend.userservice.entity.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long> {
}
