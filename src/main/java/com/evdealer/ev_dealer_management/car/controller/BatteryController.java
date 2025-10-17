package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryListGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.BatteryPostDto;
import com.evdealer.ev_dealer_management.car.model.dto.battery.PartialBatteryUpdateDto;
import com.evdealer.ev_dealer_management.car.service.BatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/battery")
@RequiredArgsConstructor
public class BatteryController {

    private final BatteryService batteryService;

    @GetMapping("/all")
    public ResponseEntity<BatteryListGetDto> getAllBatteries(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(batteryService.getAllBatteries(pageNo, pageSize));
    }

    @GetMapping("/{batteryId}/detail")
    public ResponseEntity<BatteryDetailGetDto> getDetailBatteryById(@PathVariable(name = "batteryId") Long id) {
        return ResponseEntity.ok(batteryService.getBatteryDetailById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<BatteryDetailGetDto> createNewBattery(@RequestBody BatteryPostDto batteryPostDto) {
        return ResponseEntity.ok(batteryService.addNewBattery(batteryPostDto));
    }

    @PatchMapping("/{batteryId}/update")
    public ResponseEntity<Void> updateBatteryById(
            @PathVariable(name = "batteryId") Long batteryId,
            @RequestBody PartialBatteryUpdateDto partialBatteryUpdateDto) {
        batteryService.updatePartialBatteryById(batteryId, partialBatteryUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{batteryId}/remove")
    public ResponseEntity<Void> removeBatteryById(@PathVariable(name = "batteryId") Long batteryId) {
        batteryService.removeBatteryById(batteryId);
        return ResponseEntity.noContent().build();
    }
}
