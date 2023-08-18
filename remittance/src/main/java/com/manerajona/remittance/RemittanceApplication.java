package com.manerajona.remittance;

import static org.springframework.web.servlet.function.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class RemittanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemittanceApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> transfers() {
        return route()
            .POST("/transfers", request -> ServerResponse.accepted().build())
            .build();
    }
}