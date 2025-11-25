package com.evdealer.ev_dealer_management.sale.controller;

import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramListDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramPostDto;
import com.evdealer.ev_dealer_management.sale.service.PriceProgramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-program")
@RequiredArgsConstructor
@Tag(name = "Price Program", description = "Price Program APIs")
public class PriceProgramController {

    private final PriceProgramService priceProgramService;

    @GetMapping("/all")
    public ResponseEntity<PriceProgramListDto> getAllPriceProgram(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(priceProgramService.getAllPriceProgram(pageNo, pageSize));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PriceProgramGetDto> getById(@PathVariable Long id) {
        PriceProgramGetDto priceProgram = priceProgramService.getById(id);
        return ResponseEntity.ok(priceProgram);
    }

    @GetMapping("/current-and-upcoming")
    public ResponseEntity<List<PriceProgramGetDto>> getAllCurrentAndUpComingPriceProgram() {
        return ResponseEntity.ok(priceProgramService.getCurrentAndUpcomingPriceProgram());
    }

//    @GetMapping("/hierarchy")
//    public ResponseEntity<List<PriceProgramGetDto>> getByDealerHierarchy(@RequestParam Integer level) {
//        List<PriceProgramGetDto> programs = priceProgramService.getByDealerHierarchy(level);
//        return ResponseEntity.ok(programs);
//    }

    @PostMapping
    public ResponseEntity<PriceProgramGetDto> create(@Valid @RequestBody PriceProgramPostDto dto) {
        PriceProgramGetDto created = priceProgramService.createNewPriceProgram(dto);
        return ResponseEntity.ok(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PriceProgramGetDto> updatePartialById(
            @PathVariable Long id,
            @Valid @RequestBody PriceProgramPostDto dto) {
        PriceProgramGetDto updated = priceProgramService.updatePartialById(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        priceProgramService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
