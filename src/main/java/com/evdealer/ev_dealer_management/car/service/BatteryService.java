package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Battery;

import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.repository.BatteryRepository;
import org.springframework.stereotype.Service;

@Service
public class BatteryService {

        private BatteryRepository batteryRepository;

        public Battery createBattery(BatteryPostDto batteryPostDto, Performance performance) {
            Battery battery = batteryRepository.findByChemistry(batteryPostDto.chemistry()).
                    orElseGet(() ->addBattery(batteryPostDto)) ;

            battery.getPerformances().add(performance);
            return batteryRepository.save(battery);
        }

        private   Battery addBattery(BatteryPostDto batteryPostDto) {
            Battery battery = new Battery();
            battery.setChemistry(batteryPostDto.chemistry());
            battery.setCapacityKwh(batteryPostDto.capacityKWh());
            battery.setCycleLife(batteryPostDto.cycleLife());

            return batteryRepository.save(battery);
        }


}
