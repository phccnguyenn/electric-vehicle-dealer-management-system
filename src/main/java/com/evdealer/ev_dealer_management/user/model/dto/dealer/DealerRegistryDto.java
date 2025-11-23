package com.evdealer.ev_dealer_management.user.model.dto.dealer;

import jakarta.validation.Valid;

public record DealerRegistryDto (
        @Valid DealerInfoPostDto dealerInfo,
        @Valid DealerUserPostDto dealerAccount
) {
}
