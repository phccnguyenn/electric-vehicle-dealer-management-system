package com.evdealer.ev_dealer_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for security settings.
 * Lớp này sẽ được sử dụng để cấu hình các thiết lập bảo mật
 * cho ứng dụng, bao gồm các cài đặt liên quan đến xác thực,
 * phân quyền, và các chính sách bảo mật khác:
 * - Thêm salt và sử dụng hàm băm để băm mật khẩu người dùng
 * trước khi lưu vào hoặc đối chiếu trong DB.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/admin/**").hasRole("EVM_ADMIN")
                .requestMatchers("/dealer/**").hasAnyRole("DEALER_STAFF", "DEALER_MANAGER")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
