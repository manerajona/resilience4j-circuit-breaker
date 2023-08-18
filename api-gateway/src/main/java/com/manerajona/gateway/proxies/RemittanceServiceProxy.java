package com.manerajona.gateway.proxies;

import com.manerajona.gateway.domain.Transfer;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class RemittanceServiceProxy {

    private final WebClient webClient;
    private final CircuitBreaker remittanceServiceCircuitBreaker;
    private final Retry remittanceServiceRetry;
    private final TimeLimiter remittanceServiceTimeLimiter;

    public RemittanceServiceProxy(WebClient webClient,
        CircuitBreaker remittanceServiceCircuitBreaker,
        Retry remittanceServiceRetry,
        TimeLimiter remittanceServiceTimeLimiter) {
        this.webClient = webClient;
        this.remittanceServiceCircuitBreaker = remittanceServiceCircuitBreaker;
        this.remittanceServiceRetry = remittanceServiceRetry;
        this.remittanceServiceTimeLimiter = remittanceServiceTimeLimiter;
    }

    @PostMapping("v1/transfers")
    public Mono<ResponseEntity<Void>> transferV1(Transfer transfer) {
        return webClient.post()
            .uri("/transfers")
            .body(Mono.just(transfer), Transfer.class)
            .retrieve()
            .toBodilessEntity()
            .transformDeferred(CircuitBreakerOperator.of(remittanceServiceCircuitBreaker))
            .transformDeferred(RetryOperator.of(remittanceServiceRetry))
            .transformDeferred(TimeLimiterOperator.of(remittanceServiceTimeLimiter))
            .onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }
}