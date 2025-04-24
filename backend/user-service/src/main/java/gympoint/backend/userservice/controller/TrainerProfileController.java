package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.dto.TrainerProfileDto;
import gympoint.backend.userservice.entity.TrainerProfile;
import gympoint.backend.userservice.service.TrainerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/trainer-profiles")
@RequiredArgsConstructor
public class TrainerProfileController {
    private final TrainerProfileService trainerProfileService;

    @PostMapping
    public TrainerProfile create(@RequestBody TrainerProfile profile) {
        return trainerProfileService.create(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerProfile> getById(@PathVariable Long id) {
        return trainerProfileService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public TrainerProfile update(@PathVariable Long id, @RequestBody TrainerProfile profile) {
        return trainerProfileService.update(id, profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainerProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
