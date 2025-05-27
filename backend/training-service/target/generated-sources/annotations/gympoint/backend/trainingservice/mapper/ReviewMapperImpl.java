package gympoint.backend.trainingservice.mapper;

import gympoint.backend.trainingservice.dto.ReviewDto;
import gympoint.backend.trainingservice.entity.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T14:32:27+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDto toDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId( review.getId() );
        reviewDto.setTrainerId( review.getTrainerId() );
        reviewDto.setClientId( review.getClientId() );
        reviewDto.setRating( review.getRating() );
        reviewDto.setComment( review.getComment() );
        reviewDto.setCreatedAt( review.getCreatedAt() );

        return reviewDto;
    }

    @Override
    public Review toEntity(ReviewDto reviewDto) {
        if ( reviewDto == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( reviewDto.getId() );
        review.setTrainerId( reviewDto.getTrainerId() );
        review.setClientId( reviewDto.getClientId() );
        review.setRating( reviewDto.getRating() );
        review.setComment( reviewDto.getComment() );
        review.setCreatedAt( reviewDto.getCreatedAt() );

        return review;
    }
}
