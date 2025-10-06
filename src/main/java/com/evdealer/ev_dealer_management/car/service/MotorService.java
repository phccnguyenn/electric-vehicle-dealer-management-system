package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.repository.MotorRepository;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
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

        validateDuplicateSerialNumber(null, motorPostDto.serialNumber());

        Motor motor = Motor.builder()
                .motorType(motorPostDto.motorType())
                .serialNumber(motorPostDto.serialNumber())
                .powerKw(motorPostDto.powerKw())
                .torqueNm(motorPostDto.torqueNm())
                .maxRpm(motorPostDto.maxRpm())
                .coolingType(motorPostDto.coolingType())
                .voltageRangeV(motorPostDto.voltageRangeV())
                .build();

        return motorRepository.save(motor);
    }

    private void validateDuplicateSerialNumber(Long motorId, String serialNumber) {
        if (checkExistedSerialNumber(motorId, serialNumber)) {
            throw new DuplicatedException(Constants.ErrorCode.MOTOR_ALREADY_EXIST, serialNumber);
        }
    }

    private boolean checkExistedSerialNumber(Long motorId, String serialNumber) {
        return motorRepository.findByExistedSerialNumber(serialNumber).isPresent();
    }
}
