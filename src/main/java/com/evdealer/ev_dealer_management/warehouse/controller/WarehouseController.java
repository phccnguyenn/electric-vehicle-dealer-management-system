//package com.evdealer.ev_dealer_management.warehouse.controller;
//
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarDetailsGetDto;
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarListDto;
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarPostDto;
//import com.evdealer.ev_dealer_management.warehouse.service.InventoryService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/inventory")
//@RequiredArgsConstructor
//public class WarehouseController {
//
//    private final InventoryService inventoryService;
//
//    @GetMapping(path = "/admin/{dealerId}")
//    public ResponseEntity<WarehouseCarListDto> getDealerInventory(
//            @PathVariable(name = "dealerId") Long dealerId,
//            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return ResponseEntity.ok(inventoryService.getDealerInventoryById(dealerId, pageNo, pageSize));
//    }
//
//    @PostMapping("/admin/create")
//    public ResponseEntity<WarehouseCarDetailsGetDto> createInventoryByPhoneNumber(
//            @RequestParam(required = false) Long dealerId,
//            @RequestParam(required = false) String phone,
//            @Valid @RequestBody WarehouseCarPostDto warehouseCarPostDto) {
//        return ResponseEntity.ok(inventoryService.createDealerInventory(dealerId, phone, warehouseCarPostDto));
//    }
//
//}
