package com.inditex.demo.infraestructure.adapter.out.persistence;


import com.inditex.demo.infraestructure.db.mapper.PriceMapper;
import com.inditex.demo.prices.adapters.pricedb.PriceJPARepository;
import com.inditex.demo.prices.domain.model.Price;
import com.inditex.demo.prices.domain.ports.repository.PricePersitencePort;
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

/**
 * Implementación del repositorio de precios usando R2DBC y Resilience4j.
 */
@Component
public final class PriceJpaAdapter implements PricePersitencePort {

    /** Repositorio reactivo de precios. */
    private final PriceJPARepository priceJPARepository;

    /** Circuit Breaker aplicado a la consulta. */
    private final CircuitBreaker circuitBreaker;

    /** Rate Limiter aplicado a la consulta. */
    private final RateLimiter rateLimiter;

    /** Política de reintentos. */
    private final Retry retry;

    /** Bulkhead para limitar concurrencia. */
    private final Bulkhead bulkhead;

    /** Mapper de entidad a dominio. */
    private final PriceMapper mapper;

    /**
     * Constructor del repositorio.
     *
     * @param priceRepository repositorio reactivo de precios
     * @param circuitBreaker  circuit breaker
     * @param rateLimiter     rate limiter
     * @param retry           política de reintentos
     * @param bulkhead        bulkhead para concurrencia
     * @param mapper          mapper de entidad a dominio
     */
    public PriceJpaAdapter(
            final PriceJPARepository priceRepository,
            final CircuitBreaker circuitBreaker,
            final RateLimiter rateLimiter,
            final Retry retry,
            final Bulkhead bulkhead,
            final PriceMapper mapper
    ) {
        this.priceJPARepository = priceRepository;
        this.circuitBreaker = circuitBreaker;
        this.rateLimiter = rateLimiter;
        this.retry = retry;
        this.bulkhead = bulkhead;
        this.mapper = mapper;
    }

    /**
     * Obtiene el precio preferente según fecha, producto y marca.
     *
     * @param applyDate fecha de aplicación
     * @param productId identificador del producto
     * @param brandId   identificador de la marca
     * @return precio preferente o error si no existe
     */
    @Override
    public Mono<Price> getPreferredPrice(
            final LocalDateTime applyDate,
            final Integer productId,
            final Integer brandId
    ) {
        return priceJPARepository
                .findTopByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
                        productId,
                        brandId,
                        applyDate,
                        applyDate
                )
                .transform(BulkheadOperator.of(bulkhead))
                .transform(RetryOperator.of(retry))
                .transform(CircuitBreakerOperator.of(circuitBreaker))
                .transform(RateLimiterOperator.of(rateLimiter))
                .timeout(java.time.Duration.ofSeconds(2))
                .switchIfEmpty(
                        Mono.error(new PriceNotFoundException(productId, brandId, applyDate))
                )
                .map(mapper::toDomain);
    }
}
