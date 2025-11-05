package com.evdealer.ev_dealer_management.sale.controller;

import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramPostDto;
import com.evdealer.ev_dealer_management.sale.service.PriceProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-program")
@RequiredArgsConstructor
public class PriceProgramController {

    private final PriceProgramService priceProgramService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<PriceProgramGetDto> getById(@PathVariable Long id) {
        PriceProgramGetDto priceProgram = priceProgramService.getById(id);
        return ResponseEntity.ok(priceProgram);
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<List<PriceProgramGetDto>> getByDealerHierarchy(@RequestParam Integer level) {
        List<PriceProgramGetDto> programs = priceProgramService.getByDealerHierarchy(level);
        return ResponseEntity.ok(programs);
    }

    @PostMapping
    public ResponseEntity<PriceProgramGetDto> create(@RequestBody PriceProgramPostDto dto) {
        PriceProgramGetDto created = priceProgramService.createNewPriceProgram(dto);
        return ResponseEntity.ok(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PriceProgramGetDto> updatePartialById(
            @PathVariable Long id,
            @RequestBody PriceProgramPostDto dto) {
        PriceProgramGetDto updated = priceProgramService.updatePartialById(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        priceProgramService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
