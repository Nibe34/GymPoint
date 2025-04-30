package gympoint.backend.userservice.mapper;

import gympoint.backend.userservice.dto.AdminCreateDto;
import gympoint.backend.userservice.dto.AdminDto;
import gympoint.backend.userservice.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper implements GenericMapper<Admin, AdminDto, AdminCreateDto> {
    @Override
    public AdminDto toDto(Admin entity) {
        AdminDto dto = new AdminDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        return dto;
    }

    @Override
    public Admin toEntity(AdminCreateDto createDto) {
        Admin entity = new Admin();
        entity.setEmail(createDto.getEmail());
        entity.setPassword(createDto.getPassword());
        entity.setFirstName(createDto.getFirstName());
        entity.setLastName(createDto.getLastName());
        entity.setRole(createDto.getRole());
        return entity;
    }

    @Override
    public Admin updateEntity(Admin existingEntity, AdminCreateDto updateDto) {
        existingEntity.setEmail(updateDto.getEmail());
        existingEntity.setPassword(updateDto.getPassword());
        existingEntity.setFirstName(updateDto.getFirstName());
        existingEntity.setLastName(updateDto.getLastName());
        existingEntity.setRole(updateDto.getRole());
        return existingEntity;
    }
} 