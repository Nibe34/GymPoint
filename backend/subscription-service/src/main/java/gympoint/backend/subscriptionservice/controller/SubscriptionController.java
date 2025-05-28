package gympoint.backend.subscriptionservice.controller;

import gympoint.backend.subscriptionservice.dto.SubscriptionDTO;
import gympoint.backend.subscriptionservice.mapper.SubscriptionMapper;
import gympoint.backend.subscriptionservice.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "APIs for managing subscription types")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new subscription type")
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(subscriptionMapper.toDTO(
            subscriptionService.createSubscription(subscriptionMapper.toEntity(subscriptionDTO))
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a subscription type")
    public ResponseEntity<SubscriptionDTO> updateSubscription(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(subscriptionMapper.toDTO(
            subscriptionService.updateSubscription(id, subscriptionMapper.toEntity(subscriptionDTO))
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a subscription type")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all subscription types")
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        return ResponseEntity.ok(
            subscriptionService.getAllSubscriptions().stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
} 