package com.evdealer.ev_dealer_management.testdrive.controller;

import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotDetailsGetDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotPostDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotUpdateDto;
import com.evdealer.ev_dealer_management.testdrive.service.SlotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slot")
@RequiredArgsConstructor
@Tag(name = "Driving Test", description = "Driving Test APIs for Dealer only")
public class SlotController {

    private final SlotService slotService;

    @GetMapping("/all")
    public ResponseEntity<List<SlotDetailsGetDto>> getAllSlotsByCurrentDealer() {
        List<SlotDetailsGetDto> slots = slotService.getAllSlotsByCurrentDealer();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/detail/{slotId}")
    public ResponseEntity<SlotDetailsGetDto> getSlotDetailById(@PathVariable("slotId") Long slotId) {
        SlotDetailsGetDto slot = slotService.getSlotById(slotId, SlotDetailsGetDto.class);
        return ResponseEntity.ok(slot);
    }

    @GetMapping("/available")
    public ResponseEntity<List<SlotDetailsGetDto>> getOngoingSlots() {
        List<SlotDetailsGetDto> slots = slotService.getAllOngoingSlots();
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/create")
    public ResponseEntity<SlotDetailsGetDto> createSlot(@Valid @RequestBody SlotPostDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(slotService.createSlot(dto));
    }

    @PatchMapping("/update/{slotId}")
    public ResponseEntity<SlotDetailsGetDto> updateSlot(@PathVariable Long slotId, @Valid @RequestBody SlotUpdateDto slotUpdateDto) {
        return ResponseEntity.ok(slotService.updateSlot(slotId, slotUpdateDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }

}
