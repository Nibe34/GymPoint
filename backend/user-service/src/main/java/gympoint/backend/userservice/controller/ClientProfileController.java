package gympoint.backend.userservice.controller;


import gympoint.backend.userservice.dto.ClientProfileDto;
import gympoint.backend.userservice.entity.ClientProfile;
import gympoint.backend.userservice.service.ClientProfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {
    @Autowired
    private final ClientProfileService clientProfileService;

    @PostMapping
    public ClientProfile create(@RequestBody ClientProfile profile) {
        return clientProfileService.create(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProfile> getById(@PathVariable Long id) {
        return clientProfileService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ClientProfile update(@PathVariable Long id, @RequestBody ClientProfile profile) {
        return clientProfileService.update(id, profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
