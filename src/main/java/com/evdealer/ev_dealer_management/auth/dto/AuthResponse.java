package com.evdealer.ev_dealer_management.auth.dto;

/**
 * DTO for authentication response.
 * Lớp này sẽ được sử dụng bởi AuthController để gửi dữ liệu
 * phản hồi xác thực (như token, thông tin người dùng, v.v.) trở
 * về cho client sau khi xử lý đăng nhập hoặc đăng ký (chỉ có staff mới đăng ký)
 * từ AuthService.
 */
public class AuthResponse {
    private String token;
    private String role;

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String setToken(String token) {
        return this.token = token;
    }

    public String setRole(String role) {
        return this.role = role;
    }
}
