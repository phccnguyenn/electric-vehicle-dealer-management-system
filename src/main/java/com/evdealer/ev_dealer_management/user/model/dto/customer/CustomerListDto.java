package com.evdealer.ev_dealer_management.user.model.dto.customer;

import java.util.List;

public record CustomerListDto(
        List<CustomerDetailGetDto> customerDetailGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
