package gympoint.backend.userservice.controller;

import gympoint.backend.userservice.dto.AdminCreateDto;
import gympoint.backend.userservice.dto.AdminDto;
import gympoint.backend.userservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminCreateDto adminCreateDto) {
        AdminDto createdAdmin = adminService.createAdmin(adminCreateDto);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        AdminDto admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long id, @RequestBody AdminCreateDto adminCreateDto) {
        AdminDto updatedAdmin = adminService.updateAdmin(id, adminCreateDto);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
} 