package gympoint.backend.userservice.service;


import gympoint.backend.userservice.dto.ClientProfileResponse;
import gympoint.backend.userservice.dto.ClientProfileUpdateRequest;
import gympoint.backend.userservice.entity.ClientProfile;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.mapper.ClientProfileMapper;
import gympoint.backend.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final UserRepository userRepository;
    private final ClientProfileMapper clientProfileMapper;

    @Override
    public ClientProfileResponse getByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return clientProfileMapper.toResponse(user.getClientProfile());
    }

    @Override
    public void updateProfile(Long userId, ClientProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ClientProfile profile = user.getClientProfile();
        if (profile == null) throw new RuntimeException("Client profile not found");

        clientProfileMapper.updateFromDto(request, profile);
        userRepository.save(user);
    }
}
