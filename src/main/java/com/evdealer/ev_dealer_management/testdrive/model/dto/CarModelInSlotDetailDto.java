package com.evdealer.ev_dealer_management.testdrive.model.dto;


import com.evdealer.ev_dealer_management.testdrive.model.CarModelInSlot;

public record CarModelInSlotDetailDto(
        Long carModelInSlotId,
        Long slotId,
        Long carModelId,
        Integer maxTrialCar
) {
    public static CarModelInSlotDetailDto fromModel(CarModelInSlot model) {
        return new CarModelInSlotDetailDto(
                model.getId(),
                model.getSlot().getId(),
                model.getCarModel().getId(),
                model.getMaxTrialCar()
        );
    }
}
