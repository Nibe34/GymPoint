package gympoint.backend.userservice.service;


import gympoint.backend.common.enums.Role;
import gympoint.backend.userservice.dto.*;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.mapper.*;
import gympoint.backend.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ClientProfileMapper clientProfileMapper;
    private final TrainerProfileMapper trainerProfileMapper;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(UserRegistrationRequest request) {
        User user = userMapper.toEntity(request);

        if (request.getRole() == Role.CLIENT && request.getClientProfile() != null) {
            user.setClientProfile(clientProfileMapper.toEntity(request.getClientProfile()));
        }

        if (request.getRole() == Role.TRAINER && request.getTrainerProfile() != null) {
            user.setTrainerProfile(trainerProfileMapper.toEntity(request.getTrainerProfile()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getCurrentUser() {
        // TODO зробити хендлер
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateCurrentUser(UserUpdateRequest request) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());

        if (user.getRole() == Role.CLIENT && request.getClientProfileUpdateRequest() != null) {
            clientProfileMapper.updateFromDto(request.getClientProfileUpdateRequest(), user.getClientProfile());
        }

        if (user.getRole() == Role.TRAINER && request.getTrainerProfileUpdateRequest() != null) {
            trainerProfileMapper.updateFromDto(request.getTrainerProfileUpdateRequest(), user.getTrainerProfile());
        }

        userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
