package com.inditex.demo.prices.adapters.pricedb;
import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PriceRepository;
import com.inditex.demo.prices.exceptions.PriceNotFoundException;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.bulkhead.operator.BulkheadOperator;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJPARepository priceJPARepository;
    private final CircuitBreaker circuitBreaker;
    private final RateLimiter rateLimiter;
    private final Retry retry;
    private final Bulkhead bulkhead;
    
    public PriceRepositoryImpl(PriceJPARepository priceRepository,
                        CircuitBreaker circuitBreaker,
                        RateLimiter rateLimiter,
                        Retry retry,
                        Bulkhead bulkhead) {
    	this.priceJPARepository = priceRepository;
        this.circuitBreaker = circuitBreaker;
        this.rateLimiter = rateLimiter;
        this.retry = retry;
        this.bulkhead = bulkhead;
    }

    public Mono<Price> getPreferredPrice(LocalDateTime applyDate, Integer productId, Integer brandId) {
        return priceJPARepository.findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(productId, brandId, applyDate, applyDate)
                // Bulkhead: limita concurrencia
                .transform(BulkheadOperator.of(bulkhead))
                // Retry automático
                .transform(RetryOperator.of(retry))
                .transform(CircuitBreakerOperator.of(circuitBreaker))
                // Aplica Rate Limiter
                .transform(RateLimiterOperator.of(rateLimiter))
                // Timeout de 2 segundos
                .timeout(java.time.Duration.ofSeconds(2))
                // Si no encuentra nada, lanza excepción
                .switchIfEmpty(Mono.error(new PriceNotFoundException(productId, brandId, applyDate)))
                // Si hay cualquier otro error interno, lo propaga
                ;
    }
}
