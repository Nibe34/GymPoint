package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.dto.AdminCreateDto;
import gympoint.backend.userservice.dto.AdminDto;
import gympoint.backend.userservice.entity.Admin;
import gympoint.backend.userservice.mapper.UserMapper;
import gympoint.backend.userservice.repository.AdminRepository;
import gympoint.backend.userservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserMapper userMapper;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, UserMapper userMapper) {
        this.adminRepository = adminRepository;
        this.userMapper = userMapper;
    }

    @Override
    public AdminDto createAdmin(AdminCreateDto adminCreateDto) {
        Admin admin = userMapper.toAdmin(adminCreateDto);
        Admin savedAdmin = adminRepository.save(admin);
        return userMapper.toAdminDto(savedAdmin);
    }

    @Override
    public AdminDto getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        return userMapper.toAdminDto(admin);
    }

    @Override
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(userMapper::toAdminDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto updateAdmin(Long id, AdminCreateDto adminCreateDto) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        
        Admin updatedAdmin = userMapper.toAdmin(adminCreateDto);
        updatedAdmin.setId(existingAdmin.getId());
        
        Admin savedAdmin = adminRepository.save(updatedAdmin);
        return userMapper.toAdminDto(savedAdmin);
    }

    @Override
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
} 