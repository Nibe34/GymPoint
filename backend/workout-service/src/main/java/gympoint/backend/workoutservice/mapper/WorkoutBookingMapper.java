package gympoint.backend.workoutservice.mapper;

import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import gympoint.backend.workoutservice.entity.WorkoutBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface WorkoutBookingMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorkoutBooking toEntity(WorkoutBookingDto dto);

    WorkoutBookingDto toDto(WorkoutBooking entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(WorkoutBookingDto dto, @MappingTarget WorkoutBooking entity);
} 