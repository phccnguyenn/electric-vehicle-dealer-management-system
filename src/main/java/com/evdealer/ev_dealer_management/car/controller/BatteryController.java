package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.service.BatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/battery")
@RequiredArgsConstructor
public class BatteryController {

    private final BatteryService batteryService;

    @GetMapping("/{batteryId}/detail")
    public ResponseEntity<BatteryDetailGetDto> getDetailBatteryById(@PathVariable(name = "batteryId") Long id) {
        return ResponseEntity.ok(batteryService.getBatteryDetailById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<BatteryDetailGetDto> createNewBattery(@RequestBody BatteryPostDto batteryPostDto) {
        return ResponseEntity.ok(batteryService.addNewBattery(batteryPostDto));
    }

}
