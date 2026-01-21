package com.inditex.demo.config;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Resilience4j para circuit breaker, rate limiter,
 * retry y bulkhead.
 */
@Configuration
public class Resilience4jConfig {

    /** Propiedades de configuración de Resilience4j. */
    private final ResilienceProperties props;

    /**
     * Constructor.
     *
     * @param props propiedades de Resilience4j
     */
    public Resilience4jConfig(final ResilienceProperties props) {
        this.props = props;
    }

    /**
     * Configura el Circuit Breaker para el servicio de precios.
     *
     * @return instancia de CircuitBreaker
     */
    @Bean
    public CircuitBreaker priceServiceCircuitBreaker() {
        var cb = props.getCb();

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(cb.getFailureRateThreshold())
                .waitDurationInOpenState(
                        DurationStyle.detectAndParse(cb.getWaitDurationOpen())
                )
                .slidingWindowSize(cb.getSlidingWindowSize())
                .build();

        return CircuitBreaker.of("priceServiceCircuitBreaker", config);
    }

    /**
     * Configura el Rate Limiter para el servicio de precios.
     *
     * @return instancia de RateLimiter
     */
    @Bean
    public RateLimiter priceServiceRateLimiter() {
        var rl = props.getRl();

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(rl.getLimitForPeriod())
                .limitRefreshPeriod(
                        DurationStyle.detectAndParse(rl.getLimitRefreshPeriod())
                )
                .timeoutDuration(
                        DurationStyle.detectAndParse(rl.getTimeoutDuration())
                )
                .build();

        return RateLimiter.of("priceServiceRateLimiter", config);
    }

    /**
     * Configura la política de reintentos para el servicio de precios.
     *
     * @return instancia de Retry
     */
    @Bean
    public Retry priceServiceRetry() {
        var r = props.getRetry();

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(r.getMaxAttempts())
                .waitDuration(
                        DurationStyle.detectAndParse(r.getWaitDuration())
                )
                .build();

        return Retry.of("priceServiceRetry", config);
    }

    /**
     * Configura el Bulkhead para el servicio de precios.
     *
     * @return instancia de Bulkhead
     */
    @Bean
    public Bulkhead priceServiceBulkhead() {
        var bh = props.getBh();

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(bh.getMaxConcurrentCalls())
                .maxWaitDuration(
                        DurationStyle.detectAndParse(bh.getMaxWaitDuration())
                )
                .build();

        return Bulkhead.of("priceServiceBulkhead", config);
    }
}
