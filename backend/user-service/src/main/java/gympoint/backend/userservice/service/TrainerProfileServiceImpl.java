package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.TrainerProfile;
import gympoint.backend.userservice.repository.TrainerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {
    private final TrainerProfileRepository trainerProfileRepository;

    @Override
    public TrainerProfile create(TrainerProfile profile) {
        return trainerProfileRepository.save(profile);
    }

    @Override
    public Optional<TrainerProfile> getById(Long Id) {
        return trainerProfileRepository.findById(Id);
    }

    @Override
    public TrainerProfile update(Long Id, TrainerProfile updatedProfile) {
        updatedProfile.setId(Id);
        return trainerProfileRepository.save(updatedProfile);
    }

    @Override
    public void delete(Long userId) {
        trainerProfileRepository.deleteById(userId);
    }
}