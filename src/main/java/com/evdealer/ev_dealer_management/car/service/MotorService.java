package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Motor;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorListDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.PartialMotorUpdateDto;
import com.evdealer.ev_dealer_management.car.repository.MotorRepository;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MotorService {

    private final MotorRepository motorRepository;

    public MotorListDto getAllMotors(int pageNo, int pageSize)  {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Motor> motorPage = motorRepository.findAll(pageable);

        return toMotorListGetDto(motorPage);
    }

    public Motor getMotorById(Long motorId) {
        return motorRepository.findById(motorId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.MOTOR_NOT_FOUND, motorId));
    }

    public MotorDetailGetDto getMotorDetailById(Long motorId) {
        Motor motor = getMotorById(motorId);
        return MotorDetailGetDto.fromModel(motor);
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

    public void updatePartialMotor(Long motorId, PartialMotorUpdateDto dto) {

        Motor motor = getMotorById(motorId);

        if (dto.motorType() != null && dto.motorType() != motor.getMotorType())
            motor.setMotorType(dto.motorType());

        if (dto.serialNumber() != null && !dto.serialNumber().equals(motor.getSerialNumber()))
            motor.setSerialNumber(dto.serialNumber());

        if (dto.powerKw() != null && !dto.powerKw().equals(motor.getPowerKw()))
            motor.setPowerKw(dto.powerKw());

        if (dto.torqueNm() != null && !dto.torqueNm().equals(motor.getTorqueNm()))
            motor.setTorqueNm(dto.torqueNm());

        if (dto.maxRpm() != null && !dto.maxRpm().equals(motor.getMaxRpm()))
            motor.setMaxRpm(dto.maxRpm());

        if (dto.coolingType() != null && dto.coolingType() != motor.getCoolingType())
            motor.setCoolingType(dto.coolingType());

        if (dto.voltageRangeV() != null && !dto.voltageRangeV().equals(motor.getVoltageRangeV()))
            motor.setVoltageRangeV(dto.voltageRangeV());

        motorRepository.save(motor);
    }

    public void removeMotorById(Long id) {
        Motor battery = getMotorById(id);
        motorRepository.delete(battery);
    }

    private MotorListDto toMotorListGetDto(Page<Motor> motorPage) {
        List<MotorDetailGetDto> motorDetailGetDtos = motorPage.getContent()
                .stream()
                .map(MotorDetailGetDto::fromModel)
                .toList();

        return new MotorListDto(
                motorDetailGetDtos,
                motorPage.getNumber(),
                motorPage.getSize(),
                (int) motorPage.getTotalElements(),
                motorPage.getTotalPages(),
                motorPage.isLast()
        );
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
