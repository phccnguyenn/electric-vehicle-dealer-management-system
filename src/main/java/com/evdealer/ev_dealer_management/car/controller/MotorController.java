package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.service.MotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/motor")
@RequiredArgsConstructor
public class MotorController {

    private final MotorService motorService;

    @PostMapping("/create")
    public ResponseEntity<MotorDetailGetDto> createMotor(@RequestBody MotorPostDto motorPostDto) {
        return ResponseEntity.ok(motorService.createMotor(motorPostDto));
    }

}
