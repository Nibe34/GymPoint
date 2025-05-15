package gympoint.backend.subscriptionservice.controller;

import gympoint.backend.subscriptionservice.dto.UserSubscriptionDTO;
import gympoint.backend.subscriptionservice.service.UserSubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my-subscriptions")
@RequiredArgsConstructor
@Tag(name = "User Subscriptions", description = "APIs for managing user subscriptions")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    @GetMapping
    @Operation(summary = "Get all user subscriptions")
    public ResponseEntity<List<UserSubscriptionDTO>> getMySubscriptions(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(userSubscriptionService.getUserSubscriptions(userEmail));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active subscription")
    public ResponseEntity<UserSubscriptionDTO> getActiveSubscription(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(userSubscriptionService.getActiveSubscription(userEmail));
    }

    @PostMapping("/purchase/{subscriptionId}")
    @Operation(summary = "Purchase a subscription")
    public ResponseEntity<UserSubscriptionDTO> purchaseSubscription(
            Authentication authentication,
            @PathVariable Long subscriptionId) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(userSubscriptionService.purchaseSubscription(userEmail, subscriptionId));
    }
} 