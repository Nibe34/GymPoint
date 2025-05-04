package gympoint.backend.userservice.repository;

import gympoint.backend.userservice.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends BaseUserRepository<Admin> {
}