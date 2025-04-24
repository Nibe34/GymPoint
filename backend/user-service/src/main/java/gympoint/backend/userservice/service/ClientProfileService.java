package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.ClientProfile;

import java.util.Optional;

public interface ClientProfileService {
    ClientProfile create(ClientProfile profile);
    Optional<ClientProfile> getById(Long Id);
    ClientProfile update(Long Id, ClientProfile profile);
    void delete(Long userId);
}