package gympoint.backend.trainingservice.mapper;

import gympoint.backend.trainingservice.dto.TrainingSessionDto;
import gympoint.backend.trainingservice.entity.TrainingSession;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T14:32:27+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TrainingSessionMapperImpl implements TrainingSessionMapper {

    @Override
    public TrainingSessionDto toDto(TrainingSession trainingSession) {
        if ( trainingSession == null ) {
            return null;
        }

        TrainingSessionDto trainingSessionDto = new TrainingSessionDto();

        trainingSessionDto.setId( trainingSession.getId() );
        trainingSessionDto.setTrainerId( trainingSession.getTrainerId() );
        trainingSessionDto.setStartTime( trainingSession.getStartTime() );
        trainingSessionDto.setMaxParticipants( trainingSession.getMaxParticipants() );

        return trainingSessionDto;
    }

    @Override
    public TrainingSession toEntity(TrainingSessionDto trainingSessionDto) {
        if ( trainingSessionDto == null ) {
            return null;
        }

        TrainingSession trainingSession = new TrainingSession();

        trainingSession.setId( trainingSessionDto.getId() );
        trainingSession.setTrainerId( trainingSessionDto.getTrainerId() );
        trainingSession.setStartTime( trainingSessionDto.getStartTime() );
        trainingSession.setMaxParticipants( trainingSessionDto.getMaxParticipants() );

        return trainingSession;
    }
}
