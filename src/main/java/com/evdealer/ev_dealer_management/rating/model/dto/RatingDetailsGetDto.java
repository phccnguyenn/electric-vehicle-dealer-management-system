package com.evdealer.ev_dealer_management.rating.model.dto;

import com.evdealer.ev_dealer_management.rating.model.Rating;

import java.time.OffsetDateTime;

public record RatingDetailsGetDto (
        Long id,
        String phone,
        String content,
        OffsetDateTime createdOn
) {
    public static RatingDetailsGetDto fromModel(Rating rating) {
        return new RatingDetailsGetDto(
                rating.getId(),
                rating.getPhone(),
                rating.getContent(),
                rating.getCreatedOn()
        );
    }
}
