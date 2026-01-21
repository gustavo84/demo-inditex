package com.inditex.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Propiedades de configuración para Resilience4j aplicadas al servicio de precios.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "resilience.price")
public class ResilienceProperties {

    /** Configuración del Circuit Breaker. */
    private CircuitBreakerProperties cb = new CircuitBreakerProperties();

    /** Configuración del Rate Limiter. */
    private RateLimiterProperties rl = new RateLimiterProperties();

    /** Configuración de la política de reintentos. */
    private RetryProperties retry = new RetryProperties();

    /** Configuración del Bulkhead. */
    private BulkheadProperties bh = new BulkheadProperties();

    /**
     * Propiedades del Circuit Breaker.
     */
    @Getter
    @Setter
    public static final class CircuitBreakerProperties {

        /** Umbral de tasa de fallos permitido. */
        private float failureRateThreshold;

        /** Duración de espera en estado OPEN. */
        private String waitDurationOpen;

        /** Tamaño de la ventana deslizante. */
        private int slidingWindowSize;
    }

    /**
     * Propiedades del Rate Limiter.
     */
    @Getter
    @Setter
    public static final class RateLimiterProperties {

        /** Límite de peticiones por periodo. */
        private int limitForPeriod;

        /** Duración del periodo de refresco del límite. */
        private String limitRefreshPeriod;

        /** Duración máxima de espera. */
        private String timeoutDuration;
    }

    /**
     * Propiedades de la política de reintentos.
     */
    @Getter
    @Setter
    public static final class RetryProperties {

        /** Número máximo de intentos. */
        private int maxAttempts;

        /** Duración de espera entre intentos. */
        private String waitDuration;
    }

    /**
     * Propiedades del Bulkhead.
     */
    @Getter
    @Setter
    public static final class BulkheadProperties {

        /** Número máximo de llamadas concurrentes. */
        private int maxConcurrentCalls;

        /** Duración máxima de espera. */
        private String maxWaitDuration;
    }
}
