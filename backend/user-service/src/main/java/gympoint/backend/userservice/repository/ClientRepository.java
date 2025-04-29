package gympoint.backend.userservice.repository;


import gympoint.backend.userservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByDateOfBirthBetween(LocalDate from, LocalDate to);
}