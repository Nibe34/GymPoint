package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.AdminProfile;
import gympoint.backend.userservice.repository.AdminProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProfileServiceImpl implements AdminProfileService {
    private final AdminProfileRepository adminProfileRepository;

    @Override
    public AdminProfile create(AdminProfile profile) {
        return adminProfileRepository.save(profile);
    }

    @Override
    public Optional<AdminProfile> getById(Long Id) {
        return adminProfileRepository.findById(Id);
    }

    @Override
    public AdminProfile update(Long Id, AdminProfile updatedProfile) {
        updatedProfile.setId(Id);
        return adminProfileRepository.save(updatedProfile);
    }

    @Override
    public void delete(Long userId) {
        adminProfileRepository.deleteById(userId);
    }
}
