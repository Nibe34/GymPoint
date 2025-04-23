package gympoint.backend.userservice.service;


import gympoint.backend.userservice.dto.TrainerProfileResponse;
import gympoint.backend.userservice.dto.TrainerProfileUpdateRequest;
import gympoint.backend.userservice.entity.TrainerProfile;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.mapper.TrainerProfileMapper;
import gympoint.backend.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final UserRepository userRepository;
    private final TrainerProfileMapper trainerProfileMapper;

    @Override
    public TrainerProfileResponse getByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return trainerProfileMapper.toResponse(user.getTrainerProfile());
    }

    @Override
    public void updateProfile(Long userId, TrainerProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TrainerProfile profile = user.getTrainerProfile();
        if (profile == null) throw new RuntimeException("Trainer profile not found");

        trainerProfileMapper.updateFromDto(request, profile);
        userRepository.save(user);
    }
}
