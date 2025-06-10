package gympoint.backend.authservice.client;

import gympoint.backend.authservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClientFallback implements UserServiceClient {
    
    @Override
    public ResponseEntity<?> createClient(ClientRegisterRequest request) {
        throw new RuntimeException("User service is not available. Please try again later.");
    }

    @Override
    public ResponseEntity<?> createTrainer(TrainerRegisterRequest request) {
        throw new RuntimeException("User service is not available. Please try again later.");
    }

    @Override
    public ResponseEntity<?> createAdmin(AdminRegisterRequest request) {
        throw new RuntimeException("User service is not available. Please try again later.");
    }

    @Override
    public UserDetailsResponse getUserByEmail(String email) {
        throw new RuntimeException("User service is not available. Please try again later.");
    }
} 