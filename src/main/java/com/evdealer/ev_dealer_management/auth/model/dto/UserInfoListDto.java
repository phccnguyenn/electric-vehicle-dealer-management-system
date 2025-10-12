package com.evdealer.ev_dealer_management.auth.model.dto;


import java.util.List;

public record UserInfoListDto (
        List<UserProfileGetDto> carInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
){
}
