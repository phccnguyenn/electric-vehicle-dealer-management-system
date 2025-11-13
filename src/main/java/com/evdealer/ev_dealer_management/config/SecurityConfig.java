package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
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

    /**
     * User Domain Security
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain userDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/auth/**", "/api/v1/user/**", "/api/v1/dealer/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                    auth ->
                        auth.requestMatchers("/api/v1/auth/login").permitAll()
                            .requestMatchers("/api/v1/auth/profile").hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                            .requestMatchers(HttpMethod.POST, "/api/v1/user/create").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                            .requestMatchers("/api/v1/user/change-password").hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                            .requestMatchers("/api/v1/user/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                            .requestMatchers(HttpMethod.GET, "/api/v1/dealer/staff").hasRole("DEALER_MANAGER")
                            .requestMatchers("/api/v1/dealer/**").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                            .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Product Domain Security
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain productDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/carDetail/**", "/api/v1/carDetail-model/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                    auth ->
                        auth
                            // Car Model APIs
                            .requestMatchers(HttpMethod.GET, "/api/v1/carDetail-model/all")
                                .hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                            .requestMatchers(HttpMethod.GET, "/api/v1/carDetail-model/get-trial-model-carDetail")
                                .hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                            .requestMatchers("/api/v1/carDetail-model/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF")

                            // Car Details APIs
                            .requestMatchers(HttpMethod.GET, "/api/v1/carDetail/**")
                                .hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                            .requestMatchers(
                                "/api/v1/carDetail/create",
                                "/api/v1/carDetail/*/update",
                                "/api/v1/carDetail/*/upload/images").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                            .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Warehouse Domain Security
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(3)
    public SecurityFilterChain stockDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/warehouse-carDetail/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        // create new stock for specific Dealer
                                    .requestMatchers("/api/v1/warehouse-carDetail/admin/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                    .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Order Domain Security
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(4)
    public SecurityFilterChain orderDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/orders/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                    .requestMatchers(HttpMethod.GET, "/api/v1/orders/sales-speed").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                    .requestMatchers( "/api/v1/orders/pending", "/api/v1/orders/approve-order").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/orders/{orderId}/activities").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/orders/{id}").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAnyRole("DEALER_MANAGER")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/orders/*/payments").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/orders/*/payments").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                                    .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/{id}").hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/{id}").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")

                                    .requestMatchers("/api/v1/orders/reports/revenue/staff").hasRole("DEALER_MANAGER")
                                    .requestMatchers("/api/v1/orders/reports/revenue/dealer").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                    .requestMatchers("/api/v1/orders/reports/revenue/city").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                    .requestMatchers("/api/v1/orders/reports/customer-debts").hasRole("DEALER_MANAGER")

                                    .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(5)
    public SecurityFilterChain ratingDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/rating/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(HttpMethod.POST, "/api/v1/rating/create-rating").permitAll()
                                        .requestMatchers("/api/v1/rating/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(6)
    public SecurityFilterChain driveDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/slot/**", "/api/v1/booking/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                        auth ->
                                auth
//                                        .requestMatchers(HttpMethod.GET, "/api/v1/slot/all").permitAll()
//                                        .requestMatchers("/api/v1/slot/create", "/api/v1/slot/update", "/api/v1/slot/delete/**").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
//
//                                        .requestMatchers(HttpMethod.POST, "/api/v1/booking/create").permitAll()
//                                        .requestMatchers("/api/v1/booking/slot/**", "/api/v1/booking/**").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
//                                        .anyRequest().authenticated()

                                        .requestMatchers("/api/v1/slot/**", "/api/v1/booking/**").hasAnyRole("DEALER_MANAGER", "DEALER_STAFF")
                                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(7)
    public SecurityFilterChain saleDomainSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/price-program", "api/v1/price-program/**", "api/v1/program-detail", "api/v1/program-detail/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/api/v1/price-program/detail/{id}", "api/v1/price-program/hierarchy/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF", "DEALER_MANAGER", "DEALER_STAFF")
                                        .requestMatchers(HttpMethod.POST, "/api/v1/price-program").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                        .requestMatchers(HttpMethod.PATCH, "/api/v1/price-program/{id}").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/price-program/{id}").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                        .requestMatchers("/api/v1/program-detail/details/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
                                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Swagger Security
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**")
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(
                    auth ->
                        auth
                            .requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/webjars/**"
                            ).permitAll()
                            .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
//                    @Override
//                    public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
//                        CorsConfiguration configuration = new CorsConfiguration();
//                        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//                        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//                        configuration.setExposedHeaders(List.of("x-auth-token"));
//
//                        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                        source.registerCorsConfiguration("/**", configuration);
//
//                        httpSecurityCorsConfigurer.configurationSource(source);
//                    }
//                })
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(authProvider)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers(
//                                "/v3/api-docs/**", "/swagger-ui/**",
//                                "/swagger-ui.html", "/webjars/**").permitAll()
////                        .requestMatchers("/api/v1/auth/register",
////                                "/api/v1/auth/all/profile").hasRole("EVM_ADMIN")
//                        .requestMatchers("/api/v1/user/**").hasAnyRole("EVM_ADMIN", "EVM_STAFF")
//                        .requestMatchers("/api/v1/dealer/**").hasRole("DEALER_MANAGER")
//
//                        // Product
//                        .requestMatchers(
//                                "/api/v1/battery/create",
//                                "/api/v1/battery/*/remove").hasRole("EVM_ADMIN")
//                        .requestMatchers(
//                                "/api/v1/motor/create",
//                                "/api/v1/motor/*/update",
//                                "/api/v1/motor/*/remove").hasRole("EVM_ADMIN")
//                        .requestMatchers(
//                                "/api/v1/carDetail/create",
//                                "/api/v1/carDetail/*/update",
//                                "/api/v1/carDetail/*/upload/images").hasRole("EVM_ADMIN")
//                        .requestMatchers(
//                                HttpMethod.POST, "/api/v1/category/create").hasRole("EVM_ADMIN")
//                        .requestMatchers("/api/v1/category/*/rename").hasRole("EVM_ADMIN")
//                        .anyRequest().permitAll()
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//
//    }

    private UrlBasedCorsConfigurationSource corsConfig() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://evm-system.vercel.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(List.of("x-auth-token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}