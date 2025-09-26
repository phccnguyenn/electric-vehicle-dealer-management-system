package com.evdealer.ev_dealer_management.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for login request.
 * Lớp này sẽ được sử dụng bởi AuthController để nhận & đóng gói dữ liệu
 * đăng nhập từ client và xử lý nghiệp vụ (tức là validate dữ liệu) từ AuthService
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "Username can not be blanked")
    private String username;

    @NotBlank(message = "Password can not be blanked")
    private String password;
}
