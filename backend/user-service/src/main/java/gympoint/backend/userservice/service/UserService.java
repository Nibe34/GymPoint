package gympoint.backend.userservice.service;


import gympoint.backend.userservice.dto.UserRegistrationRequest;
import gympoint.backend.userservice.dto.UserResponse;
import gympoint.backend.userservice.dto.UserUpdateRequest;

public interface UserService {
    UserResponse register(UserRegistrationRequest request);
    UserResponse getCurrentUser();
    UserResponse updateCurrentUser(UserUpdateRequest request);
}
