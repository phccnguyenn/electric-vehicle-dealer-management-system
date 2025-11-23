package com.evdealer.ev_dealer_management.user.model.dto.dealer;

import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DealerUserPostDto(

        Long dealerInfoId,

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Full name must be at most 100 characters")
        String fullName,

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Phone is required")
        String phone,

        @NotBlank(message = "City is required")
        String city,

        boolean isActive,

        @NotNull(message = "Role is required")
        RoleType role

) {
}
