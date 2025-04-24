package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.entity.AdminProfile;
import gympoint.backend.userservice.service.AdminProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin-profiles")
@RequiredArgsConstructor
public class AdminProfileController {
    private final AdminProfileService adminProfileService;

    @PostMapping
    public AdminProfile create(@RequestBody AdminProfile profile) {
        return adminProfileService.create(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminProfile> getById(@PathVariable Long id) {
        return adminProfileService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public AdminProfile update(@PathVariable Long id, @RequestBody AdminProfile profile) {
        return adminProfileService.update(id, profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
