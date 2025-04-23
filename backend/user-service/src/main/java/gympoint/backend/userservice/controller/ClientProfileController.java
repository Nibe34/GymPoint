package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.dto.ClientProfileResponse;
import gympoint.backend.userservice.dto.ClientProfileUpdateRequest;
import gympoint.backend.userservice.service.ClientProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ClientProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(clientProfileService.getByUserId(userId));
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<Void> updateProfile(@PathVariable Long userId,
                                              @RequestBody ClientProfileUpdateRequest request) {
        clientProfileService.updateProfile(userId, request);
        return ResponseEntity.noContent().build();
    }
}
