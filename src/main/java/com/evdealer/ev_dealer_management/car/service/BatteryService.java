package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Battery;

import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryListGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.PartialBatteryUpdateDto;
import com.evdealer.ev_dealer_management.car.repository.BatteryRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatteryService {

    private final BatteryRepository batteryRepository;

    public BatteryListGetDto getAllBatteries(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Battery> batteryPage = batteryRepository.findAll(pageable);

        return toBatteryListGetDto(batteryPage);
    }

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

    public void updatePartialBatteryById(Long batteryId, PartialBatteryUpdateDto dto) {
        Battery battery = batteryRepository.findById(batteryId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.BATTERY_NOT_FOUND, batteryId));

        if (dto.chemistryType() != null && !dto.chemistryType().equals(battery.getChemistryType()))
            battery.setChemistryType(dto.chemistryType());

        if (dto.age() != null && !dto.age().equals(battery.getAge()))
            battery.setAge(dto.age());

        if (dto.chargeTime() != null && !dto.chargeTime().equals(battery.getChargeTime()))
            battery.setChargeTime(dto.chargeTime());

        if (dto.usageDuration() != null && !dto.usageDuration().equals(battery.getUsageDuration()))
            battery.setUsageDuration(dto.usageDuration());

        if (dto.weightKg() != null && !dto.weightKg().equals(battery.getWeightKg()))
            battery.setWeightKg(dto.weightKg());

        if (dto.voltageV() != null && !dto.voltageV().equals(battery.getVoltageV()))
            battery.setVoltageV(dto.voltageV());

        if (dto.capacityKwh() != null && !dto.capacityKwh().equals(battery.getCapacityKwh()))
            battery.setCapacityKwh(dto.capacityKwh());

        if (dto.cycleLife() != null && !dto.cycleLife().equals(battery.getCycleLife()))
            battery.setCycleLife(dto.cycleLife());

        batteryRepository.save(battery);
    }

    public void removeBatteryById(Long batteryId) {
        Battery battery = batteryRepository.findById(batteryId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.BATTERY_NOT_FOUND, batteryId));
        batteryRepository.delete(battery);
    }

    private BatteryListGetDto toBatteryListGetDto(Page<Battery> batteryPage) {

        List<BatteryDetailGetDto> batteryDetailGetDto = batteryPage.getContent()
                .stream()
                .map(BatteryDetailGetDto::fromModel)
                .toList();

        return new BatteryListGetDto(
                batteryDetailGetDto,
                batteryPage.getNumber(),
                batteryPage.getSize(),
                (int) batteryPage.getTotalElements(),
                batteryPage.getTotalPages(),
                batteryPage.isLast()
        );
    }
}
