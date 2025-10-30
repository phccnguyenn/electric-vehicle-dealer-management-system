package com.evdealer.ev_dealer_management.stock.controller;

import com.evdealer.ev_dealer_management.stock.model.dto.InventoryDetailsGetDto;
import com.evdealer.ev_dealer_management.stock.model.dto.InventoryListDto;
import com.evdealer.ev_dealer_management.stock.model.dto.InventoryPostDto;
import com.evdealer.ev_dealer_management.stock.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping(path = "/admin/{dealerId}")
    public ResponseEntity<InventoryListDto> getDealerInventory(
            @PathVariable(name = "dealerId") Long dealerId,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(inventoryService.getDealerInventoryById(dealerId, pageNo, pageSize));
    }

    @PostMapping("/admin/create")
    public ResponseEntity<InventoryDetailsGetDto> createInventoryByPhoneNumber(
            @RequestParam(required = false) Long dealerId,
            @RequestParam(required = false) String phone,
            @Valid @RequestBody InventoryPostDto inventoryPostDto) {
        return ResponseEntity.ok(inventoryService.createDealerInventory(dealerId, phone, inventoryPostDto));
    }

}
