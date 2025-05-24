package gympoint.backend.trainerservice.mapper;

import gympoint.backend.trainerservice.dto.ReviewDto;
import gympoint.backend.trainerservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "trainerId", source = "trainerId")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "createdAt", source = "createdAt")
    ReviewDto toDto(Review review);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "trainerId", source = "trainerId")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "createdAt", source = "createdAt")
    Review toEntity(ReviewDto reviewDto);
} 