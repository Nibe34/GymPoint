package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.TrainerProfile;

import java.util.Optional;

public interface TrainerProfileService {
    TrainerProfile create(TrainerProfile profile);
    Optional<TrainerProfile> getById(Long Id);
    TrainerProfile update(Long userId, TrainerProfile profile);
    void delete(Long userId);
}