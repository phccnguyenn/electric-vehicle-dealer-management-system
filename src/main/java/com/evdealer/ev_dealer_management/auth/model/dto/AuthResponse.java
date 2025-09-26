package com.evdealer.ev_dealer_management.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO for authentication response.
 * Lớp này sẽ được sử dụng bởi AuthController để gửi dữ liệu
 * phản hồi xác thực (như token, thông tin người dùng, v.v.) trở
 * về cho client sau khi xử lý đăng nhập hoặc đăng ký (chỉ có staff mới đăng ký)
 * từ AuthService.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
