package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.user.model.User;
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
                "com.evdealer.ev_dealer_management.user.repository",
                "com.evdealer.ev_dealer_management.car.repository",
                "com.evdealer.ev_dealer_management.warehouse.repository",
                "com.evdealer.ev_dealer_management.order.repository",
                "com.evdealer.ev_dealer_management.rating.repository",
                "com.evdealer.ev_dealer_management.testdrive.repository",
                "com.evdealer.ev_dealer_management.sale.repository"
        }
)
@EntityScan(
        basePackages = {
                "com.evdealer.ev_dealer_management.auth.model",
                "com.evdealer.ev_dealer_management.user.model",
                "com.evdealer.ev_dealer_management.car.model",
                "com.evdealer.ev_dealer_management.warehouse.model",
                "com.evdealer.ev_dealer_management.order.model",
                "com.evdealer.ev_dealer_management.rating.model",
                "com.evdealer.ev_dealer_management.testdrive.model",
                "com.evdealer.ev_dealer_management.sale.model"
        }
)
public class DatabaseAutoConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                return Optional.empty();
            }

            User userDetails = (User) authentication.getPrincipal();
            //return Optional.of(authentication.getName());
            return  Optional.of(userDetails.getFullName());
        };
    }

}
