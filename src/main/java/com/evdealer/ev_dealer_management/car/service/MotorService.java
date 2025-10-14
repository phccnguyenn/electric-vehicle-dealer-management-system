package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.repository.MotorRepository;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
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

    public Motor getMotorById(Long motorId) {
        return motorRepository.findById(motorId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.MOTOR_NOT_FOUND, motorId));
    }

    public MotorDetailGetDto createMotor(MotorPostDto motorPostDto) {

        // Check the motor serial number
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

        return MotorDetailGetDto.fromModel(motorRepository.save(motor));
    }

    private void validateDuplicateSerialNumber(Long motorId, String serialNumber) {
        if (checkExistedSerialNumber(motorId, serialNumber)) {
            throw new DuplicatedException(Constants.ErrorCode.SERIAL_NUMBER_ALREADY_EXISTS, serialNumber);
        }
    }

    private boolean checkExistedSerialNumber(Long motorId, String serialNumber) {
        return motorRepository.existsBySerialNumber(serialNumber);
    }
}
