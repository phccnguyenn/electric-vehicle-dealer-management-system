package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.auth.filter.JwtAuthenticationFilter;
import com.evdealer.ev_dealer_management.auth.repository.TokenRepository;
import com.evdealer.ev_dealer_management.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                        configuration.setExposedHeaders(List.of("x-auth-token"));

                        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                        source.registerCorsConfiguration("/**", configuration);

                        httpSecurityCorsConfigurer.configurationSource(source);
                    }
                })
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**", "/swagger-ui/**",
                                "/swagger-ui.html", "/webjars/**").permitAll()
                        .requestMatchers("/api/v1/auth/register",
                                "/api/v1/auth/all/profile").hasRole("EVM_ADMIN")
                        .requestMatchers(
                                "/api/v1/battery/create").hasRole("EVM_ADMIN")
                        .requestMatchers(
                                "/api/v1/motor/create").hasRole("EVM_ADMIN")
                        .requestMatchers(
                                "/api/v1/car/create",
                                "/api/v1/car/{carId}/update",
                                "/api/v1/car/{carId}/upload/images").hasRole("EVM_ADMIN")
                        .requestMatchers(
                                HttpMethod.POST, "/api/v1/category/create").hasRole("EVM_ADMIN")
                        .requestMatchers("/api/v1/category/{categoryId}/rename").hasRole("EVM_ADMIN")
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();

    }

}