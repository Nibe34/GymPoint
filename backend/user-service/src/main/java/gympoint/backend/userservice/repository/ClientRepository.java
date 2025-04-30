package gympoint.backend.userservice.repository;

import gympoint.backend.userservice.entity.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends BaseUserRepository<Client> {
}