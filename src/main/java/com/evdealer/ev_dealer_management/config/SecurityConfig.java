package com.evdealer.ev_dealer_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for security settings.
 * Lớp này sẽ được sử dụng để cấu hình các thiết lập bảo mật
 * cho ứng dụng, bao gồm các cài đặt liên quan đến xác thực,
 * phân quyền, và các chính sách bảo mật khác:
 * - Thêm salt và sử dụng hàm băm để băm mật khẩu người dùng
 * trước khi lưu vào hoặc đối chiếu trong DB.
 */

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
