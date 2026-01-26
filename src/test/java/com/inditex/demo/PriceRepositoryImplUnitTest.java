package com.inditex.demo;

import com.inditex.demo.infraestructure.adapter.out.persistence.PriceJpaAdapter;
import com.inditex.demo.infraestructure.db.PriceEntity;
import com.inditex.demo.infraestructure.db.mapper.PriceMapper;
import com.inditex.demo.prices.adapters.out.persitence.PriceJPARepository;
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

    private PriceJpaAdapter priceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

        PriceMapper mapper = new PriceMapper();

        priceRepository = new PriceJpaAdapter(
                priceJPARepository,
                circuitBreaker,
                rateLimiter,
                retry,
                bulkhead,
                mapper
        );
    }

    @Test
    void testGetPreferredPriceFound() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");

        PriceEntity entity = new PriceEntity();
        entity.setProductId(35455);
        entity.setBrandId(1);
        entity.setPriceList(1);
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        entity.setPrice(35.50);

        when(priceJPARepository
                .findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                        35455, 1, date, date))
                .thenReturn(Mono.just(entity));

        Price result = priceRepository.getPreferredPrice(date, 35455, 1);

        assertNotNull(result);
        assertEquals(35.50, result.getPrice());
        assertEquals(35455, result.getProductId());
        assertEquals(1, result.getBrandId());
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
                () -> priceRepository.getPreferredPrice(date, 35455, 1)
        );
    }
}
