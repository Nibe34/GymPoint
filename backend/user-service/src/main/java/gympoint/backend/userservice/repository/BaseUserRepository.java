package gympoint.backend.userservice.repository;

import gympoint.backend.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserRepository<E extends User> extends JpaRepository<E, Long> {
    Optional<E> findByEmail(String email);
} 