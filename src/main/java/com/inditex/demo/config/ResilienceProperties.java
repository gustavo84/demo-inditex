package com.inditex.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "resilience.price")
public class ResilienceProperties {

    private CircuitBreakerProperties cb = new CircuitBreakerProperties();
    private RateLimiterProperties rl = new RateLimiterProperties();
    private RetryProperties retry = new RetryProperties();
    private BulkheadProperties bh = new BulkheadProperties();

    @Getter @Setter
    public static class CircuitBreakerProperties {
        private float failureRateThreshold;
        private String waitDurationOpen;
        private int slidingWindowSize;
    }

    @Getter @Setter
    public static class RateLimiterProperties {
        private int limitForPeriod;
        private String limitRefreshPeriod;
        private String timeoutDuration;
    }

    @Getter @Setter
    public static class RetryProperties {
        private int maxAttempts;
        private String waitDuration;
    }

    @Getter @Setter
    public static class BulkheadProperties {
        private int maxConcurrentCalls;
        private String maxWaitDuration;
    }
}
