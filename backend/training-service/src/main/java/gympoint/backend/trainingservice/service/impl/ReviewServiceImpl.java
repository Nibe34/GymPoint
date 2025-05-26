package gympoint.backend.trainingservice.service.impl;

import gympoint.backend.trainingservice.dto.ReviewDto;
import gympoint.backend.trainingservice.entity.Review;
import gympoint.backend.trainingservice.mapper.ReviewMapper;
import gympoint.backend.trainingservice.repository.ReviewRepository;
import gympoint.backend.trainingservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                           ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ReviewDto createReview(ReviewDto reviewDto) {
        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        if (hasClientReviewedTrainer(reviewDto.getClientId(), reviewDto.getTrainerId())) {
            throw new IllegalStateException("Client has already reviewed this trainer");
        }

        Review review = reviewMapper.toEntity(reviewDto);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toDto(updatedReview);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByTrainerId(Long trainerId) {
        return reviewRepository.findByTrainerId(trainerId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByClientId(Long clientId) {
        return reviewRepository.findByClientId(clientId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasClientReviewedTrainer(Long clientId, Long trainerId) {
        return reviewRepository.existsByTrainerIdAndClientId(trainerId, clientId);
    }
} 