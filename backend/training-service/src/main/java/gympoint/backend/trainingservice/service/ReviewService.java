package gympoint.backend.trainingservice.service;

import gympoint.backend.trainingservice.dto.ReviewDto;
import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    ReviewDto updateReview(Long id, ReviewDto reviewDto);
    void deleteReview(Long id);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getReviewsByTrainerId(Long trainerId);
    List<ReviewDto> getReviewsByClientId(Long clientId);
    boolean hasClientReviewedTrainer(Long clientId, Long trainerId);
}