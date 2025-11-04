package com.evdealer.ev_dealer_management.testdrive.controller;

import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotDetailsGetDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotPostDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotUpdateDto;
import com.evdealer.ev_dealer_management.testdrive.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slot")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    @PostMapping("/create")
    public ResponseEntity<SlotDetailsGetDto> createSlot(@RequestBody SlotPostDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(slotService.createSlot(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SlotDetailsGetDto>> getAllSlotsByDealer() {
        List<SlotDetailsGetDto> slots = slotService.getAllSlotByDealer();
        return ResponseEntity.ok(slots);
    }

    @PatchMapping("/update")
    public ResponseEntity<SlotDetailsGetDto> updateSlot(
            @RequestBody SlotUpdateDto slotUpdateDto
    ) {

        return ResponseEntity.ok(slotService.updateSlot(slotUpdateDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }

}
