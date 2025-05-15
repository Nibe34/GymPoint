package gympoint.backend.userservice.controller;

import gympoint.backend.userservice.dto.AuthResponseDto;
import gympoint.backend.userservice.dto.LoginDto;
import gympoint.backend.userservice.dto.RefreshTokenRequestDto;
import gympoint.backend.userservice.dto.RegisterDto;
import gympoint.backend.userservice.security.JwtTokenProvider;
import gympoint.backend.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        logger.debug("Attempting login for user: {}", loginDto.getEmail());
        
        try {
            AuthResponseDto response = userService.authenticateUser(loginDto);
            logger.debug("Login successful for user: {}", loginDto.getEmail());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials for user: {}", loginDto.getEmail());
            return ResponseEntity.status(401).body("Invalid email or password");
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", loginDto.getEmail(), e);
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during login for user: {}", loginDto.getEmail(), e);
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Get a new access token using refresh token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto request) {
        logger.debug("Attempting to refresh token");
        try {
            AuthResponseDto response = userService.refreshToken(request);
            logger.debug("Token refresh successful");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid refresh token request: {}", e.getMessage());
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Token refresh failed", e);
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or email already taken")
    })
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        logger.debug("Attempting to register user: {}", registerDto.getEmail());
        
        if (userService.existsByEmail(registerDto.getEmail())) {
            logger.warn("Registration failed - email already taken: {}", registerDto.getEmail());
            return ResponseEntity.badRequest().body("Email is already taken!");
        }

        if (registerDto.getRole() == null) {
            logger.warn("Registration failed - role is required for user: {}", registerDto.getEmail());
            return ResponseEntity.badRequest().body("Role is required!");
        }

        try {
            userService.createUserWithProfile(registerDto);
            logger.debug("User registered successfully: {}", registerDto.getEmail());
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed for user: {}", registerDto.getEmail(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 