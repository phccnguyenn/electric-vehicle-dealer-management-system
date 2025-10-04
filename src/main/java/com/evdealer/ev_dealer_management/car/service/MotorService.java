package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.repository.MotorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MotorService {

    private final MotorRepository motorRepository;

    public Motor createMotor(MotorPostDto motorPostDto) {
        Motor motor = motorRepository.findByMotorType(motorPostDto.motorType())
                .orElseGet(() -> addMotor(motorPostDto));
        return motorRepository.save(motor);
    }
    private Motor addMotor(MotorPostDto motorPostDto) {
        Motor motor = new Motor();
        motor.setMotorType(motorPostDto.motorType());
        motor.setSerialNumber(motorPostDto.serialNumber());
        motor.setPowerKw(motorPostDto.powerKw());
        motor.setTorqueNm(motorPostDto.torqueNm());
        motor.setMaxRpm(motorPostDto.maxRpm());
        motor.setCoolingType(motorPostDto.coolingType());
        motor.setVoltageRangeV(motorPostDto.voltageRangeV());
        motor.setWeightKg(motorPostDto.weightKg());
        return motorRepository.save(motor);
    }

}
