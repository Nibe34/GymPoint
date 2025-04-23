package gympoint.backend.userservice.service;


import gympoint.backend.userservice.dto.ClientProfileResponse;
import gympoint.backend.userservice.dto.ClientProfileUpdateRequest;

public interface ClientProfileService {
    ClientProfileResponse getByUserId(Long userId);
    void updateProfile(Long userId, ClientProfileUpdateRequest request);
}
