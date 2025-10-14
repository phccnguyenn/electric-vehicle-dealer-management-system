package com.evdealer.ev_dealer_management.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(
        basePackages = {
                "com.evdealer.ev_dealer_management.auth.repository",
                "com.evdealer.ev_dealer_management.car.repository"
        }
)
@EntityScan(
        basePackages = {
                "com.evdealer.ev_dealer_management.auth.model",
                "com.evdealer.ev_dealer_management.car.model"
        }
)
public class DatabaseAutoConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                return Optional.of("");
            }

            return Optional.of(authentication.getName());
        };
    }

}
