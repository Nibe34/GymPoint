package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.ClientProfile;
import gympoint.backend.userservice.repository.ClientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {
    private final ClientProfileRepository clientProfileRepository;

    @Override
    public ClientProfile create(ClientProfile profile) {
        return clientProfileRepository.save(profile);
    }

    @Override
    public Optional<ClientProfile> getById(Long Id) {
        return clientProfileRepository.findById(Id);
    }

    @Override
    public ClientProfile update(Long Id, ClientProfile updatedProfile) {
        updatedProfile.setId(Id);
        return clientProfileRepository.save(updatedProfile);
    }

    @Override
    public void delete(Long userId) {
        clientProfileRepository.deleteById(userId);
    }
}