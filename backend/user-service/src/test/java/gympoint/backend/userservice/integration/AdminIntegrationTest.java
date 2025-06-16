package gympoint.backend.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gympoint.backend.common.enums.Role;
import gympoint.backend.userservice.dto.AdminCreateDto;
import gympoint.backend.userservice.dto.AdminDto;
import gympoint.backend.userservice.entity.Admin;
import gympoint.backend.userservice.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class AdminIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdminRepository adminRepository;

    private AdminCreateDto adminCreateDto;

    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();

        adminCreateDto = new AdminCreateDto();
        adminCreateDto.setEmail("admin@example.com");
        adminCreateDto.setPassword("password123");
        adminCreateDto.setFirstName("John");
        adminCreateDto.setLastName("Doe");
        adminCreateDto.setRole(Role.ROLE_ADMIN);
        adminCreateDto.setDepartment("IT");
        adminCreateDto.setPosition("System Administrator");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateAdmin() throws Exception {
        // when
        mockMvc.perform(post("/api/users/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(adminCreateDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(adminCreateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(adminCreateDto.getLastName()))
                .andExpect(jsonPath("$.department").value(adminCreateDto.getDepartment()))
                .andExpect(jsonPath("$.position").value(adminCreateDto.getPosition()));

        // then
        List<Admin> admins = adminRepository.findAll();
        assertThat(admins).hasSize(1);
        assertThat(admins.get(0).getEmail()).isEqualTo(adminCreateDto.getEmail());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAdminById() throws Exception {
        // given
        Admin savedAdmin = adminRepository.save(createAdmin());

        // when/then
        mockMvc.perform(get("/api/users/admins/{id}", savedAdmin.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedAdmin.getId()))
                .andExpect(jsonPath("$.email").value(savedAdmin.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAdminByEmail() throws Exception {
        // given
        Admin savedAdmin = adminRepository.save(createAdmin());

        // when/then
        mockMvc.perform(get("/api/users/admins/email/{email}", savedAdmin.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(savedAdmin.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateAdmin() throws Exception {
        // given
        Admin savedAdmin = adminRepository.save(createAdmin());
        adminCreateDto.setFirstName("Jane");
        adminCreateDto.setDepartment("HR");
        adminCreateDto.setPosition("HR Manager");

        // when/then
        mockMvc.perform(put("/api/users/admins/{id}", savedAdmin.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.department").value("HR"))
                .andExpect(jsonPath("$.position").value("HR Manager"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteAdmin() throws Exception {
        // given
        Admin savedAdmin = adminRepository.save(createAdmin());

        // when/then
        mockMvc.perform(delete("/api/users/admins/{id}", savedAdmin.getId()))
                .andExpect(status().isNoContent());

        assertThat(adminRepository.findById(savedAdmin.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllAdmins() throws Exception {
        // given
        Admin admin1 = createAdmin();
        admin1.setEmail("admin1@example.com");
        adminRepository.save(admin1);

        Admin admin2 = createAdmin();
        admin2.setEmail("admin2@example.com");
        adminRepository.save(admin2);

        // when/then
        mockMvc.perform(get("/api/users/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    private Admin createAdmin() {
        Admin admin = new Admin();
        admin.setEmail("admin@example.com");
        admin.setPassword("password123");
        admin.setFirstName("John");
        admin.setLastName("Doe");
        admin.setRole(Role.ROLE_ADMIN);
        admin.setDepartment("IT");
        admin.setPosition("System Administrator");
        return admin;
    }
} 