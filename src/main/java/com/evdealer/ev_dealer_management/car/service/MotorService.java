package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.repository.MotorRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
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

        validateSerialNumber(motorPostDto.serialNumber());


        Motor motor = new Motor();
        motor.setMotorType(motorPostDto.motorType());
        motor.setSerialNumber(motorPostDto.serialNumber());
        motor.setPowerKw(motorPostDto.powerKw());
        motor.setTorqueNm(motorPostDto.torqueNm());
        motor.setMaxRpm(motorPostDto.maxRpm());
        motor.setCoolingType(motorPostDto.coolingType());
        motor.setVoltageRangeV(motorPostDto.voltageRangeV());;
        return motorRepository.save(motor);
    }

    private void validateSerialNumber(String serialNumber) {
        if (serialNumber == null || serialNumber.trim().isEmpty()|| serialNumber.length() == 17) {
            throw new NotFoundException(Constants.ErrorCode.MOTOR_INVALID_SERIAL, serialNumber);
        }

        motorRepository.findBySerialNumber(serialNumber)
                .ifPresent(existing -> {
                    throw new NotFoundException(Constants.ErrorCode.SERIAL_NUMBER_ALREADY_EXISTS, serialNumber);
                });
    }

}
