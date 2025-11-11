package com.evdealer.ev_dealer_management.warehouse.controller;

import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarDetailsGetDto;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarListDto;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarPostDto;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarUpdateDto;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import com.evdealer.ev_dealer_management.warehouse.service.WarehouseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouse-car")
@RequiredArgsConstructor
@Tag(name = "Warehouse", description = "Warehouse Management for Manufacturer")
public class WarehouseController {

    private final WarehouseService warehouseService;

//    @GetMapping(path = "/admin/{dealerId}")
//    public ResponseEntity<WarehouseCarListDto> getDealerInventory(
//            @PathVariable(name = "dealerId") Long dealerId,
//            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return ResponseEntity.ok(warehouseService.getDealerInventoryById(dealerId, pageNo, pageSize));
//    }

    @GetMapping("/admin/all")
    public ResponseEntity<WarehouseCarListDto> getAllWarehouseCar(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(warehouseService.getAllWarehouseCar(pageNo, pageSize));
    }

    @GetMapping("/admin/warehouse-car-status")
    public ResponseEntity<WarehouseCarListDto> getAllWarehouseCar(
            @RequestParam(name = "warehouseCarStatus", required = false) WarehouseCarStatus warehouseCarStatus,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(
                warehouseService.getAllWarehouseCarByWarehouseCarStatus(pageNo, pageSize, warehouseCarStatus)
        );
    }

    @PostMapping("/admin/create")
    public ResponseEntity<WarehouseCarDetailsGetDto> createWarehouseCar(
            @Valid @RequestBody WarehouseCarPostDto warehouseCarPostDto) {
        return ResponseEntity.ok(warehouseService.createWarehouseWithCarDetail(warehouseCarPostDto));
    }

    @PatchMapping("/admin/update/{warehouseCarId}")
    public ResponseEntity<Void> updateWarehouseCar(
            @PathVariable(name = "warehouseCarId") Long warehouseCarId,
            @Valid @RequestBody WarehouseCarUpdateDto warehouseCarUpdateDto) {
        warehouseService.updateWarehouseCar(warehouseCarId, warehouseCarUpdateDto);
        return ResponseEntity.noContent().build();
    }

}
