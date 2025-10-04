package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Battery;
import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.repository.BatteryRepository;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import com.evdealer.ev_dealer_management.car.repository.PerformanceRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;

public class PerformanceService {

    private PerformanceRepository performanceRepository;
    private CarRepository carRepository;
    private BatteryRepository batteryRepository;

    public PerformanceDetailGetDto createPerformance(Long carId, PerformancePostDto performancePostDto) {;
        Car car = carRepository.findById(carId).
                orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_NOT_FOUND));

        Performance perf = performanceRepository.findById(carId)
                .orElse(new Performance()) ;

        perf.setCar(car);

        // set battery
        if (performancePostDto.batteryId() != null) {
            Battery battery = batteryRepository.findById(performancePostDto.batteryId())
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.BATTERY_NOT_FOUND, performancePostDto.batteryId()));
            perf.setBattery(battery);
        }
        perf.setRangeMiles(performancePostDto.rangeMiles());
        perf.setAccelerationSec(performancePostDto.accelerationSec());
        perf.setTopSpeedMph(performancePostDto.topSpeedMph());

        Car savedCar = carRepository.save(car);
        Performance savedPerformance = performanceRepository.save(perf);

        return null;
    }

}
