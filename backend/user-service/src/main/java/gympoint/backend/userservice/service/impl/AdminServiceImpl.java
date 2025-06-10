package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.AdminCreateDto;
import gympoint.backend.userservice.dto.AdminDto;
import gympoint.backend.userservice.entity.Admin;
import gympoint.backend.userservice.mapper.AdminMapper;
import gympoint.backend.userservice.repository.AdminRepository;
import gympoint.backend.userservice.service.AbstractUserService;
import gympoint.backend.userservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl extends AbstractUserService<Admin, AdminDto, AdminCreateDto, AdminRepository> implements AdminService {

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
        super(adminRepository, adminMapper);
    }

    @Override
    public AdminDto createAdmin(AdminCreateDto adminCreateDto) {
        return createUser(adminCreateDto);
    }

    @Override
    public AdminDto getAdminById(Long id) {
        return getUserById(id);
    }

    @Override
    public List<AdminDto> getAllAdmins() {
        return getAllUsers();
    }

    @Override
    public AdminDto updateAdmin(Long id, AdminCreateDto adminCreateDto) {
        Admin existingAdmin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        
        adminCreateDto.setPassword(existingAdmin.getPassword()); // Preserve existing password
        return updateUser(id, adminCreateDto);
    }

    @Override
    public void deleteAdmin(Long id) {
        deleteUser(id);
    }

    @Override
    public AdminDto getAdminByEmail(String email) {
        Admin admin = repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Admin not found with email: " + email));
        return mapper.toDto(admin);
    }
} 