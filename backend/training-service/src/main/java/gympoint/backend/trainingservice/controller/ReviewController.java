package gympoint.backend.trainingservice.controller;

import gympoint.backend.trainingservice.dto.ReviewDto;
import gympoint.backend.trainingservice.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "Review management APIs")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(summary = "Create a new review")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.createReview(reviewDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing review")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a review by ID")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get all reviews for a trainer")
    public ResponseEntity<List<ReviewDto>> getReviewsByTrainerId(@PathVariable Long trainerId) {
        return ResponseEntity.ok(reviewService.getReviewsByTrainerId(trainerId));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get all reviews by a client")
    public ResponseEntity<List<ReviewDto>> getReviewsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(reviewService.getReviewsByClientId(clientId));
    }
/*
    @GetMapping("/check")
    @Operation(summary = "Check if a client has reviewed a trainer")
    public ResponseEntity<Boolean> hasClientReviewedTrainer(
            @RequestParam Long clientId,
            @RequestParam Long trainerId) {
        return ResponseEntity.ok(reviewService.hasClientReviewedTrainer(clientId, trainerId));
    }*/
}

