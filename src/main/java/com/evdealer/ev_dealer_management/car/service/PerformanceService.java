package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Battery;
import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.repository.BatteryRepository;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import com.evdealer.ev_dealer_management.car.repository.MotorRepository;
import com.evdealer.ev_dealer_management.car.repository.PerformanceRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final CarRepository carRepository;
    private final  BatteryRepository batteryRepository;
    private final MotorRepository motorRepository   ;

    public Performance createPerformance(Long carId, PerformancePostDto performancePostDto) {;

        Car car = carRepository.findById(carId).
                orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_NOT_FOUND));

        Performance performance = performanceRepository.findById(carId)
                .orElse(new Performance());

        // Set Car
        performance.setCar(car);

        // Set battery
        if (performancePostDto.batteryId() != null) {
            Battery battery = batteryRepository.findById(performancePostDto.batteryId())
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.BATTERY_NOT_FOUND, performancePostDto.batteryId()));
            performance.setBattery(battery);
        }

        // Set motor
        if (performancePostDto.motorId() != null) {
            Motor motor = motorRepository.findById(performancePostDto.motorId())
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.MOTOR_NOT_FOUND, performancePostDto.motorId()));
            performance.setMotor(motor);
        }

        performance.setRangeMiles(performancePostDto.rangeMiles());
        performance.setAccelerationSec(performancePostDto.accelerationSec());
        performance.setTopSpeedMph(performancePostDto.topSpeedMph());
        performance.setTowingLbs(performancePostDto.towingLbs());

        // return PerformanceDetailGetDto.fromModel(performance);
        return performance;
    }

}
