package gympoint.backend.trainerservice.mapper;

import gympoint.backend.trainerservice.dto.TrainingTypeDto;
import gympoint.backend.trainerservice.entity.TrainingType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T15:28:20+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TrainingTypeMapperImpl implements TrainingTypeMapper {

    @Override
    public TrainingTypeDto toDto(TrainingType trainingType) {
        if ( trainingType == null ) {
            return null;
        }

        TrainingTypeDto trainingTypeDto = new TrainingTypeDto();

        trainingTypeDto.setId( trainingType.getId() );
        trainingTypeDto.setName( trainingType.getName() );
        trainingTypeDto.setDescription( trainingType.getDescription() );
        trainingTypeDto.setPrice( trainingType.getPrice() );
        trainingTypeDto.setTrainerId( trainingType.getTrainerId() );

        return trainingTypeDto;
    }

    @Override
    public TrainingType toEntity(TrainingTypeDto trainingTypeDto) {
        if ( trainingTypeDto == null ) {
            return null;
        }

        TrainingType trainingType = new TrainingType();

        trainingType.setId( trainingTypeDto.getId() );
        trainingType.setName( trainingTypeDto.getName() );
        trainingType.setDescription( trainingTypeDto.getDescription() );
        trainingType.setPrice( trainingTypeDto.getPrice() );
        trainingType.setTrainerId( trainingTypeDto.getTrainerId() );

        return trainingType;
    }
}
