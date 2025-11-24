package com.evdealer.ev_dealer_management.user.model.dto.dealer;

import com.evdealer.ev_dealer_management.user.model.DealerInfo;

public record DealerInfoGetDto (
        Long dealerInfoId,
        String dealerName,
        String phone,
        Integer dealerLevel,
        String location,
        String contractFileUrl
) {
    public static DealerInfoGetDto fromModel(DealerInfo dealerInfo) {
        return new DealerInfoGetDto(
                dealerInfo.getId(),
                dealerInfo.getDealerName(),
                dealerInfo.getDealerPhone(),
                dealerInfo.getDealerHierarchy().getLevelType(),
                dealerInfo.getLocation(),
                dealerInfo.getContractFileUrl()
        );
    }
}
