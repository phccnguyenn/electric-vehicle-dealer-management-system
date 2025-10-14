package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Battery;

import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.repository.BatteryRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatteryService {

    private final BatteryRepository batteryRepository;

    public BatteryDetailGetDto getBatteryDetailById(Long batteryId) {
        Battery battery = getBatteryById(batteryId);
        return BatteryDetailGetDto.fromModel(battery);
    }

    public Battery getBatteryById(Long batteryId) {
        return batteryRepository.findById(batteryId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.BATTERY_NOT_FOUND, batteryId));
    }

    public BatteryDetailGetDto addNewBattery(BatteryPostDto batteryPostDto) {
        Battery battery = Battery.builder()
                .chemistryType(batteryPostDto.chemistryType())
                .age(batteryPostDto.age())
                .chargeTime(batteryPostDto.chargeTime())
                .usageDuration(batteryPostDto.usageDuration())
                .weightKg(batteryPostDto.weightKg())
                .voltageV(batteryPostDto.voltageV())
                .capacityKwh(batteryPostDto.capacityKWh())
                .cycleLife(batteryPostDto.cycleLife())
                .build();

        return BatteryDetailGetDto.fromModel(batteryRepository.save(battery));
    }


}
