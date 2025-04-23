package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.dto.TrainerProfileResponse;
import gympoint.backend.userservice.dto.TrainerProfileUpdateRequest;
import gympoint.backend.userservice.service.TrainerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<TrainerProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(trainerProfileService.getByUserId(userId));
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<Void> updateProfile(@PathVariable Long userId,
                                              @RequestBody TrainerProfileUpdateRequest request) {
        trainerProfileService.updateProfile(userId, request);
        return ResponseEntity.noContent().build();
    }
}
