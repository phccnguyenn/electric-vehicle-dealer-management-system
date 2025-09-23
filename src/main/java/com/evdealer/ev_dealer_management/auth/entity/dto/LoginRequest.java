package com.evdealer.ev_dealer_management.auth.entity.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for login request.
 * Lớp này sẽ được sử dụng bởi AuthController để nhận & đóng gói dữ liệu
 * đăng nhập từ client và xử lý nghiệp vụ (tức là validate dữ liệu) từ AuthService
 *
 */
public class LoginRequest {
    @NotBlank(message = "Username can not be blanked")
    private String username;

    @NotBlank(message = "Password can not be blanked")
    private String password;

    public LoginRequest() {}
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
