package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Battery;

import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.repository.BatteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatteryService {

        private final BatteryRepository batteryRepository;

        public Battery createBattery(BatteryPostDto batteryPostDto) {
            Battery battery = batteryRepository.findByChemistry(batteryPostDto.chemistry()).
                    orElseGet(() -> addBattery(batteryPostDto)) ;


            return batteryRepository.save(battery);
        }

        private   Battery addBattery(BatteryPostDto batteryPostDto) {


            Battery battery = new Battery();
            battery.setChemistry(batteryPostDto.chemistry());
            battery.setAge(batteryPostDto.age());
            battery.setChargeTime(batteryPostDto.chargeTime());
            battery.setUsageDuration(batteryPostDto.usageDuration());
            battery.setWeightKg(batteryPostDto.weightKg());
            battery.setVoltageV(batteryPostDto.voltageV());
            battery.setCapacityKwh(batteryPostDto.capacityKWh());
            battery.setCycleLife(batteryPostDto.cycleLife());

            return batteryRepository.save(battery);
        }


}
