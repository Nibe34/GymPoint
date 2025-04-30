package gympoint.backend.userservice.mapper;

import gympoint.backend.userservice.dto.ClientCreateDto;
import gympoint.backend.userservice.dto.ClientDto;
import gympoint.backend.userservice.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper implements GenericMapper<Client, ClientDto, ClientCreateDto> {
    @Override
    public ClientDto toDto(Client entity) {
        ClientDto dto = new ClientDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setAddress(entity.getAddress());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmergencyContact(entity.getEmergencyContact());
        return dto;
    }

    @Override
    public Client toEntity(ClientCreateDto createDto) {
        Client entity = new Client();
        entity.setEmail(createDto.getEmail());
        entity.setPassword(createDto.getPassword());
        entity.setFirstName(createDto.getFirstName());
        entity.setLastName(createDto.getLastName());
        entity.setRole(createDto.getRole());
        entity.setAddress(createDto.getAddress());
        entity.setDateOfBirth(createDto.getDateOfBirth());
        entity.setPhoneNumber(createDto.getPhoneNumber());
        entity.setEmergencyContact(createDto.getEmergencyContact());
        return entity;
    }

    @Override
    public Client updateEntity(Client existingEntity, ClientCreateDto updateDto) {
        existingEntity.setEmail(updateDto.getEmail());
        existingEntity.setPassword(updateDto.getPassword());
        existingEntity.setFirstName(updateDto.getFirstName());
        existingEntity.setLastName(updateDto.getLastName());
        existingEntity.setRole(updateDto.getRole());
        existingEntity.setAddress(updateDto.getAddress());
        existingEntity.setDateOfBirth(updateDto.getDateOfBirth());
        existingEntity.setPhoneNumber(updateDto.getPhoneNumber());
        existingEntity.setEmergencyContact(updateDto.getEmergencyContact());
        return existingEntity;
    }
} 