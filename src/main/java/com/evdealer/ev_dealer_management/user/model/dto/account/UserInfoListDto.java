package com.evdealer.ev_dealer_management.user.model.dto.account;


import java.util.List;

public record UserInfoListDto (
        List<UserProfileGetDto> userInfoGetDtos,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
){
}
