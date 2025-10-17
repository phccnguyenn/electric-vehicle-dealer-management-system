package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorListDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.MotorPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.motor.PartialMotorUpdateDto;
import com.evdealer.ev_dealer_management.car.service.MotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/motor")
@RequiredArgsConstructor
public class MotorController {

    private final MotorService motorService;

    @GetMapping("/all")
    public ResponseEntity<MotorListDto> getAllMotors(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(motorService.getAllMotors(pageNo, pageSize));
    }

    @GetMapping("/{motorId}/detail")
    public ResponseEntity<MotorDetailGetDto> getMotorDetailById(@PathVariable(name = "motorId") Long id) {
        return ResponseEntity.ok(motorService.getMotorDetailById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MotorDetailGetDto> createMotor(@RequestBody MotorPostDto motorPostDto) {
        return ResponseEntity.ok(motorService.createMotor(motorPostDto));
    }

    @PatchMapping("/{motorId}/update")
    public ResponseEntity<Void> updatePartialMotor(
            @PathVariable(name = "motorId") Long motorId,
            @RequestBody PartialMotorUpdateDto partialMotorUpdateDto) {
        motorService.updatePartialMotor(motorId, partialMotorUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{motorId}/remove")
    public ResponseEntity<Void> removeMotorById(@PathVariable(name = "motorId") Long motorId) {
        motorService.removeMotorById(motorId);
        return ResponseEntity.noContent().build();
    }

}
