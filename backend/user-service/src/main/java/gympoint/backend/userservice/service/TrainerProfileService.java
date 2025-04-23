package gympoint.backend.userservice.service;


import gympoint.backend.userservice.dto.TrainerProfileResponse;
import gympoint.backend.userservice.dto.TrainerProfileUpdateRequest;

public interface TrainerProfileService {
    TrainerProfileResponse getByUserId(Long userId);
    void updateProfile(Long userId, TrainerProfileUpdateRequest request);
}
