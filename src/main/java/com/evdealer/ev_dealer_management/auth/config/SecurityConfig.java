package com.evdealer.ev_dealer_management.auth.config;

import com.evdealer.ev_dealer_management.auth.filter.JwtAuthenticationFilter;
import com.evdealer.ev_dealer_management.auth.repository.TokenRepository;
import com.evdealer.ev_dealer_management.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for security settings.
 * Lớp này sẽ được sử dụng để cấu hình các thiết lập bảo mật
 * cho ứng dụng, bao gồm các cài đặt liên quan đến xác thực,
 * phân quyền, và các chính sách bảo mật khác:
 * - Thêm salt và sử dụng hàm băm để băm mật khẩu người dùng
 * trước khi lưu vào hoặc đối chiếu trong DB.
 */

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtService, userDetailsService, tokenRepository);

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/authenticate").permitAll()
//                        .requestMatchers("/admin/**").hasRole("EVM_ADMIN")
//                        .requestMatchers("/dealer/**").hasAnyRole("DEALER_STAFF", "DEALER_MANAGER")
                        .anyRequest().permitAll()
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}