package gympoint.backend.userservice.service;

import gympoint.backend.userservice.dto.AdminCreateDto;
import gympoint.backend.userservice.dto.AdminDto;

import java.util.List;

public interface AdminService {
    AdminDto createAdmin(AdminCreateDto adminCreateDto);
    AdminDto getAdminById(Long id);
    List<AdminDto> getAllAdmins();
    AdminDto updateAdmin(Long id, AdminCreateDto adminCreateDto);
    void deleteAdmin(Long id);
    AdminDto getAdminByEmail(String email);
} 