package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.AdminProfile;

import java.util.Optional;

public interface AdminProfileService {
    AdminProfile create(AdminProfile profile);
    Optional<AdminProfile> getById(Long Id);
    AdminProfile update(Long userId, AdminProfile profile);
    void delete(Long userId);
}
