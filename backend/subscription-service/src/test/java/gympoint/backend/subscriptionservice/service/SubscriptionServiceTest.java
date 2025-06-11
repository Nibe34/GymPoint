package gympoint.backend.subscriptionservice.service;

import gympoint.backend.subscriptionservice.entity.Subscription;
import gympoint.backend.subscriptionservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

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
    }

    @Test
    void createSubscription_Success() {
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        Subscription result = subscriptionService.createSubscription(subscription);

        assertNotNull(result);
        assertEquals(subscription.getName(), result.getName());
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void updateSubscription_Success() {
        when(subscriptionRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        Subscription result = subscriptionService.updateSubscription(1L, subscription);

        assertNotNull(result);
        assertEquals(subscription.getName(), result.getName());
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void updateSubscription_NotFound() {
        when(subscriptionRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> subscriptionService.updateSubscription(1L, subscription));
    }

    @Test
    void deleteSubscription_Success() {
        when(subscriptionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(subscriptionRepository).deleteById(1L);

        assertDoesNotThrow(() -> subscriptionService.deleteSubscription(1L));
        verify(subscriptionRepository).deleteById(1L);
    }

    @Test
    void deleteSubscription_NotFound() {
        when(subscriptionRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> subscriptionService.deleteSubscription(1L));
    }

    @Test
    void getAllSubscriptions_Success() {
        List<Subscription> subscriptions = Arrays.asList(subscription);
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        List<Subscription> results = subscriptionService.getAllSubscriptions();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(subscription.getName(), results.get(0).getName());
    }

    @Test
    void getSubscriptionById_Success() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        Subscription result = subscriptionService.getSubscriptionById(1L);

        assertNotNull(result);
        assertEquals(subscription.getId(), result.getId());
    }

    @Test
    void getSubscriptionById_NotFound() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> subscriptionService.getSubscriptionById(1L));
    }
} 