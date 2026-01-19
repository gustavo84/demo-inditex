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

@Configuration
public class Resilience4jConfig {

    private final ResilienceProperties props;

    public Resilience4jConfig(ResilienceProperties props) {
        this.props = props;
    }

    // --- Circuit Breaker ---
    @Bean
    public CircuitBreaker priceServiceCircuitBreaker() {
        var cb = props.getCb();

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(cb.getFailureRateThreshold())
                .waitDurationInOpenState(DurationStyle.detectAndParse(cb.getWaitDurationOpen()))
                .slidingWindowSize(cb.getSlidingWindowSize())
                .build();

        return CircuitBreaker.of("priceServiceCircuitBreaker", config);
    }

    // --- Rate Limiter ---
    @Bean
    public RateLimiter priceServiceRateLimiter() {
        var rl = props.getRl();

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(rl.getLimitForPeriod())
                .limitRefreshPeriod(DurationStyle.detectAndParse(rl.getLimitRefreshPeriod()))
                .timeoutDuration(DurationStyle.detectAndParse(rl.getTimeoutDuration()))
                .build();

        return RateLimiter.of("priceServiceRateLimiter", config);
    }

    // --- Retry ---
    @Bean
    public Retry priceServiceRetry() {
        var r = props.getRetry();

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(r.getMaxAttempts())
                .waitDuration(DurationStyle.detectAndParse(r.getWaitDuration()))
                .build();

        return Retry.of("priceServiceRetry", config);
    }

    // --- Bulkhead ---
    @Bean
    public Bulkhead priceServiceBulkhead() {
        var bh = props.getBh();

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(bh.getMaxConcurrentCalls())
                .maxWaitDuration(DurationStyle.detectAndParse(bh.getMaxWaitDuration()))
                .build();

        return Bulkhead.of("priceServiceBulkhead", config);
    }
}
