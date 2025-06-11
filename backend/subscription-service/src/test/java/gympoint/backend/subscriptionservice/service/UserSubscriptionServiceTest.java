package gympoint.backend.subscriptionservice.service;

import gympoint.backend.subscriptionservice.dto.UserSubscriptionDTO;
import gympoint.backend.subscriptionservice.entity.Subscription;
import gympoint.backend.subscriptionservice.entity.UserSubscription;
import gympoint.backend.subscriptionservice.mapper.UserSubscriptionMapper;
import gympoint.backend.subscriptionservice.repository.UserSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSubscriptionServiceTest {

    @Mock
    private UserSubscriptionRepository userSubscriptionRepository;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private UserSubscriptionMapper userSubscriptionMapper;

    @InjectMocks
    private UserSubscriptionService userSubscriptionService;

    private UserSubscription userSubscription;
    private UserSubscriptionDTO userSubscriptionDTO;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setName("Basic Plan");
        subscription.setDescription("Basic gym access");
        subscription.setPrice(new BigDecimal("29.99"));
        subscription.setDurationDays(30);
        subscription.setVisitsNumber(10);

        userSubscription = new UserSubscription();
        userSubscription.setId(1L);
        userSubscription.setUserEmail("test@example.com");
        userSubscription.setSubscription(subscription);
        userSubscription.setStartDate(LocalDateTime.now().minusDays(31));
        userSubscription.setEndDate(LocalDateTime.now().minusDays(1));
        userSubscription.setIsActive(false);
        userSubscription.setRemainingVisits(0);

        userSubscriptionDTO = new UserSubscriptionDTO();
        userSubscriptionDTO.setId(1L);
        userSubscriptionDTO.setUserEmail("test@example.com");
        userSubscriptionDTO.setStartDate(userSubscription.getStartDate());
        userSubscriptionDTO.setEndDate(userSubscription.getEndDate());
        userSubscriptionDTO.setIsActive(false);
        userSubscriptionDTO.setRemainingVisits(0);
    }

    @Test
    void getUserSubscriptions_Success() {
        when(userSubscriptionRepository.findByUserEmail(any())).thenReturn(Arrays.asList(userSubscription));
        when(userSubscriptionMapper.toDTO(any())).thenReturn(userSubscriptionDTO);

        List<UserSubscriptionDTO> result = userSubscriptionService.getUserSubscriptions("test@example.com");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userSubscriptionDTO, result.get(0));
    }

    @Test
    void getActiveSubscription_Success() {
        userSubscription.setEndDate(LocalDateTime.now().plusDays(30));
        userSubscription.setIsActive(true);
        userSubscriptionDTO.setEndDate(userSubscription.getEndDate());
        userSubscriptionDTO.setIsActive(true);

        when(userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(any())).thenReturn(Optional.of(userSubscription));
        when(userSubscriptionMapper.toDTO(any())).thenReturn(userSubscriptionDTO);

        UserSubscriptionDTO result = userSubscriptionService.getActiveSubscription("test@example.com");

        assertNotNull(result);
        assertTrue(result.getIsActive());
        assertEquals(userSubscriptionDTO, result);
    }

    @Test
    void getActiveSubscription_NotFound() {
        when(userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userSubscriptionService.getActiveSubscription("test@example.com"));
        assertEquals("No active subscription found", exception.getMessage());
    }

    @Test
    void purchaseSubscription_Success() {
        when(subscriptionService.getSubscriptionById(any())).thenReturn(subscription);
        when(userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(any())).thenReturn(Optional.empty());
        when(userSubscriptionRepository.save(any())).thenReturn(userSubscription);
        when(userSubscriptionMapper.toDTO(any())).thenReturn(userSubscriptionDTO);

        UserSubscriptionDTO result = userSubscriptionService.purchaseSubscription("test@example.com", 1L);

        assertNotNull(result);
        assertEquals(userSubscriptionDTO, result);
        verify(userSubscriptionRepository).save(any());
    }

    @Test
    void purchaseSubscription_AlreadyHasActive() {
        userSubscription.setEndDate(LocalDateTime.now().plusDays(30));
        userSubscription.setIsActive(true);

        when(userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(any())).thenReturn(Optional.of(userSubscription));

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userSubscriptionService.purchaseSubscription("test@example.com", 1L));
        assertEquals("User already has an active subscription", exception.getMessage());
    }

    @Test
    void getActiveSubscription_Expired() {
        userSubscription.setEndDate(LocalDateTime.now().minusDays(1));
        userSubscription.setIsActive(false);
        userSubscriptionDTO.setEndDate(userSubscription.getEndDate());
        userSubscriptionDTO.setIsActive(false);

        when(userSubscriptionRepository.findFirstByUserEmailAndIsActiveTrue(any())).thenReturn(Optional.of(userSubscription));
        when(userSubscriptionMapper.toDTO(any())).thenReturn(userSubscriptionDTO);

        UserSubscriptionDTO result = userSubscriptionService.getActiveSubscription("test@example.com");

        assertNotNull(result);
        assertFalse(result.getIsActive());
        assertEquals(userSubscriptionDTO, result);
    }
} 