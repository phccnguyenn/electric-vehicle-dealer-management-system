package com.evdealer.ev_dealer_management.rating.model.dto;

import java.util.List;

public record RatingListDto(
        List<RatingDetailsGetDto> ratingDetailsGetDtoList,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
