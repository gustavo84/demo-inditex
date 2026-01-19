package com.inditex.demo;

import com.inditex.demo.prices.adapters.pricedb.PriceJPARepository;
import com.inditex.demo.prices.adapters.pricedb.PriceRepositoryImpl;
import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import io.github.resilience4j.bulkhead.*;
import io.github.resilience4j.circuitbreaker.*;
import io.github.resilience4j.ratelimiter.*;
import io.github.resilience4j.retry.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRepositoryImplUnitTest {

    @Mock
    private PriceJPARepository priceJPARepository;

    private PriceRepositoryImpl priceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Instancias REALES de Resilience4j
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testCB");

        RateLimiter rateLimiter = RateLimiter.of("testRL",
                RateLimiterConfig.custom()
                        .limitRefreshPeriod(Duration.ofSeconds(1))
                        .limitForPeriod(10)
                        .timeoutDuration(Duration.ofMillis(50))
                        .build()
        );

        Retry retry = Retry.ofDefaults("testRetry");

        Bulkhead bulkhead = Bulkhead.ofDefaults("testBH");

        priceRepository = new PriceRepositoryImpl(
                priceJPARepository,
                circuitBreaker,
                rateLimiter,
                retry,
                bulkhead
        );
    }

    @Test
    void testGetPreferredPriceFound() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");

        Price price = new Price();
        price.setProductId(35455);
        price.setBrandId(1);
        price.setPrice(35.50);

        when(priceJPARepository
                .findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                        35455, 1, date, date))
                .thenReturn(Mono.just(price));

        Price result = priceRepository.getPreferredPrice(date, 35455, 1).block();

        assertNotNull(result);
        assertEquals(35.50, result.getPrice());
    }

    @Test
    void testGetPreferredPriceNotFound() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");

        when(priceJPARepository
                .findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                        35455, 1, date, date))
                .thenReturn(Mono.empty());

        assertThrows(
                PriceNotFoundException.class,
                () -> priceRepository.getPreferredPrice(date, 35455, 1).block()
        );
    }
}
