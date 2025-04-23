package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.dto.UserResponse;
import gympoint.backend.userservice.dto.UserUpdateRequest;
import gympoint.backend.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateCurrentUser(request));
    }
}
