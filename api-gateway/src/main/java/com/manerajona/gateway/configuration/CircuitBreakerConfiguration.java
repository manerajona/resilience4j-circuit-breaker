package com.manerajona.gateway.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CircuitBreakerConfiguration {

    @Bean
    CircuitBreaker remittanceServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("remittance-service");
    }

    @Bean
    Retry remittanceServiceRetry(RetryRegistry registry) {
        return registry.retry("remittance-service");
    }

    @Bean
    TimeLimiter remittanceServiceTimeLimiter(TimeLimiterRegistry registry) {
        return registry.timeLimiter("remittance-service");
    }
}