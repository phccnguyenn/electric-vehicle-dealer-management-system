package com.evdealer.ev_dealer_management.sale.controller;

import com.evdealer.ev_dealer_management.sale.model.dto.ProgramDetailGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.ProgramDetailPostDto;
import com.evdealer.ev_dealer_management.sale.service.ProgramDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/program-detail")
@RequiredArgsConstructor
public class ProgramDetailController {

    private final ProgramDetailService programDetailService;

    @PostMapping("/add-detail/{priceProgramId}")
    public ResponseEntity<ProgramDetailGetDto> addDetailForSpecProgram(
            @PathVariable(name = "priceProgramId") Long priceProgramId,
            @RequestBody ProgramDetailPostDto programDetailPostDto
            ) {
        return ResponseEntity.ok(programDetailService.addDetailForSpecPriceProgram(priceProgramId, programDetailPostDto));
    }

    @PatchMapping("/details/{detailId}")
    public ResponseEntity<ProgramDetailGetDto> updateProgramDetail(
            @PathVariable("detailId") Long detailId,
            @RequestBody ProgramDetailPostDto updateDto
    ) {
        ProgramDetailGetDto dto =
                programDetailService.updateProgramDetail(detailId, updateDto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/details/{detailId}")
    public ResponseEntity<Void> deleteProgramDetail(@PathVariable("detailId") Long detailId) {
        programDetailService.deleteProgramDetail(detailId);
        return ResponseEntity.noContent().build();
    }

}
