package com.evdealer.ev_dealer_management.user.model.dto;

import com.evdealer.ev_dealer_management.user.model.Customer;

import java.time.OffsetDateTime;

public record CustomerDetailGetDto (
        Long id,
        String fullName,
        String email,
        String phone,
        String address,
        String createdBy,
        OffsetDateTime createdOn,
        String lastModifiedBy,
        OffsetDateTime lastModifiedOn
) {
    public static CustomerDetailGetDto fromModel (Customer customer) {
        return new CustomerDetailGetDto(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getCreatedBy(),
                customer.getCreatedOn(),
                customer.getLastModifiedBy(),
                customer.getLastModifiedOn()
        );
    }
}
