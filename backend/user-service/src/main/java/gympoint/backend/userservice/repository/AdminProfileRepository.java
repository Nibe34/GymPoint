package gympoint.backend.userservice.repository;


import gympoint.backend.userservice.entity.AdminProfile;
import gympoint.backend.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProfileRepository extends JpaRepository<AdminProfile, Long> {
    Optional<User> findById(long Id);
}
