package gympoint.backend.workoutservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gympoint.backend.workoutservice.dto.WorkoutBookingDto;
import gympoint.backend.workoutservice.entity.WorkoutBooking;
import gympoint.backend.workoutservice.repository.WorkoutBookingRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class WorkoutBookingIntegrationTest {

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
    private WorkoutBookingRepository workoutBookingRepository;

    private WorkoutBookingDto workoutBookingDto;

    @BeforeEach
    void setUp() {
        workoutBookingRepository.deleteAll();

        workoutBookingDto = new WorkoutBookingDto();
        workoutBookingDto.setTrainingSessionId(1L);
        workoutBookingDto.setClientId(1L);
        workoutBookingDto.setTrainerId(1L);
        workoutBookingDto.setCreatedAt(LocalDateTime.now());
        workoutBookingDto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldCreateWorkoutBooking() throws Exception {
        // when
        mockMvc.perform(post("/api/workout-bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutBookingDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.trainingSessionId").value(workoutBookingDto.getTrainingSessionId()))
                .andExpect(jsonPath("$.clientId").value(workoutBookingDto.getClientId()))
                .andExpect(jsonPath("$.trainerId").value(workoutBookingDto.getTrainerId()));

        // then
        List<WorkoutBooking> bookings = workoutBookingRepository.findAll();
        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0).getTrainingSessionId()).isEqualTo(workoutBookingDto.getTrainingSessionId());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldGetWorkoutBookingById() throws Exception {
        // given
        WorkoutBooking savedBooking = workoutBookingRepository.save(createWorkoutBooking());

        // when/then
        mockMvc.perform(get("/api/workout-bookings/{id}", savedBooking.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedBooking.getId()))
                .andExpect(jsonPath("$.trainingSessionId").value(savedBooking.getTrainingSessionId()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldGetWorkoutBookingsByClientId() throws Exception {
        // given
        WorkoutBooking savedBooking = workoutBookingRepository.save(createWorkoutBooking());

        // when/then
        mockMvc.perform(get("/api/workout-bookings/client/{clientId}", savedBooking.getClientId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientId").value(savedBooking.getClientId()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldUpdateWorkoutBooking() throws Exception {
        // given
        WorkoutBooking savedBooking = workoutBookingRepository.save(createWorkoutBooking());
        workoutBookingDto.setTrainingSessionId(2L);

        // when/then
        mockMvc.perform(put("/api/workout-bookings/{id}", savedBooking.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutBookingDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainingSessionId").value(2L));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldDeleteWorkoutBooking() throws Exception {
        // given
        WorkoutBooking savedBooking = workoutBookingRepository.save(createWorkoutBooking());

        // when/then
        mockMvc.perform(delete("/api/workout-bookings/{id}", savedBooking.getId()))
                .andExpect(status().isNoContent());

        assertThat(workoutBookingRepository.findById(savedBooking.getId())).isEmpty();
    }

    private WorkoutBooking createWorkoutBooking() {
        WorkoutBooking booking = new WorkoutBooking();
        booking.setTrainingSessionId(1L);
        booking.setClientId(1L);
        booking.setTrainerId(1L);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        return booking;
    }
} 