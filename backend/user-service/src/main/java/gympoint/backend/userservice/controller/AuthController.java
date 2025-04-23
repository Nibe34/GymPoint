package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.dto.UserLoginRequest;
import gympoint.backend.userservice.dto.UserRegistrationRequest;
import gympoint.backend.userservice.dto.UserResponse;
import gympoint.backend.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        // TODO JWT
        return ResponseEntity.ok("Logged in (тут буде токен)");
    }
}
