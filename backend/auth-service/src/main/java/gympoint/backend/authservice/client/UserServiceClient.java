package gympoint.backend.authservice.client;

import gympoint.backend.authservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "user-service",
    path = "/api",
    fallback = UserServiceClientFallback.class
)
public interface UserServiceClient {
    
    @PostMapping("/users/clients")
    ResponseEntity<?> createClient(@RequestBody ClientRegisterRequest request);
    
    @PostMapping("/users/trainers")
    ResponseEntity<?> createTrainer(@RequestBody TrainerRegisterRequest request);
    
    @PostMapping("/users/admins")
    ResponseEntity<?> createAdmin(@RequestBody AdminRegisterRequest request);

    @GetMapping("/users/details/email/{email}")
    UserDetailsResponse getUserByEmail(@PathVariable("email") String email);
} 