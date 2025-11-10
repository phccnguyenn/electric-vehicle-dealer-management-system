package com.evdealer.ev_dealer_management.sale.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.car.service.CarModelService;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.sale.model.PriceProgram;
import com.evdealer.ev_dealer_management.sale.model.ProgramDetail;
import com.evdealer.ev_dealer_management.sale.model.dto.ProgramDetailGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.ProgramDetailPostDto;
import com.evdealer.ev_dealer_management.sale.repository.PriceProgramRepository;
import com.evdealer.ev_dealer_management.sale.repository.ProgramDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProgramDetailService {

    private final CarModelService carModelService;
    private final PriceProgramRepository priceProgramRepository;
    private final ProgramDetailRepository programDetailRepository;

    public ProgramDetailGetDto addDetailForSpecPriceProgram(Long priceProgramId,
                                                             ProgramDetailPostDto programDetailPostDto) {

        validatePriceRange(programDetailPostDto.minPrice(), programDetailPostDto.suggestedPrice(), programDetailPostDto.maxPrice());

        PriceProgram priceProgram = priceProgramRepository.findById(priceProgramId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND, priceProgramId));

        CarModel carModel = carModelService.getCarModelById(programDetailPostDto.carModelId(), CarModel.class);
        ProgramDetail programDetail = ProgramDetail.builder()
                .carModel(carModel)
                .priceProgram(priceProgram)
                .minPrice(programDetailPostDto.minPrice())
                .maxPrice(programDetailPostDto.maxPrice())
                .suggestedPrice(programDetailPostDto.suggestedPrice())
                .build();

        ProgramDetail savedProgramDetail = programDetailRepository.save(programDetail);
        priceProgram.getProgramDetails().add(savedProgramDetail);
        priceProgramRepository.save(priceProgram);

        return ProgramDetailGetDto.fromModel(savedProgramDetail);
    }

    public ProgramDetailGetDto updateProgramDetail(Long detailId, ProgramDetailPostDto updateDto) {

        validatePriceRange(updateDto.minPrice(), updateDto.suggestedPrice(), updateDto.maxPrice());

        ProgramDetail existingDetail = programDetailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PROGRAM_DETAIL_NOT_FOUND, detailId));

        // Optional: update car if ID is provided
        if (updateDto.carModelId() != null) {
            CarModel carModel = carModelService.getCarModelById(updateDto.carModelId(), CarModel.class);
            existingDetail.setCarModel(carModel);
        }

        // Update fields
        if (updateDto.minPrice() != null &&
                !updateDto.minPrice().equals(existingDetail.getMinPrice())) {
            existingDetail.setMinPrice(updateDto.minPrice());
        }

        if (updateDto.maxPrice() != null &&
                !updateDto.maxPrice().equals(existingDetail.getMaxPrice())) {
            existingDetail.setMaxPrice(updateDto.maxPrice());
        }

        if (updateDto.suggestedPrice() != null &&
                !updateDto.suggestedPrice().equals(existingDetail.getSuggestedPrice())) {
            existingDetail.setSuggestedPrice(updateDto.suggestedPrice());
        }

        ProgramDetail updated = programDetailRepository.save(existingDetail);
        return ProgramDetailGetDto.fromModel(updated);
    }

    public void deleteProgramDetail(Long detailId) {
        ProgramDetail programDetail = programDetailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PROGRAM_DETAIL_NOT_FOUND, detailId));

        // remove from priceProgram (avoid stale relation)
        PriceProgram priceProgram = programDetail.getPriceProgram();
        if (priceProgram != null) {
            priceProgram.getProgramDetails().remove(programDetail);
            priceProgramRepository.save(priceProgram);
        }

        programDetailRepository.delete(programDetail);
    }

    private void validatePriceRange(BigDecimal minPrice, BigDecimal suggestedPrice, BigDecimal maxPrice) {

        // Ensure all prices are positive
        if (minPrice.compareTo(BigDecimal.ZERO) < 0
                || suggestedPrice.compareTo(BigDecimal.ZERO) < 0
                || maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Prices must be positive values.");
        }

        // Min must be less than max
        if (minPrice.compareTo(maxPrice) >= 0) {
            throw new IllegalArgumentException("Min price must be less than max price.");
        }

        // Suggested must be strictly between min and max
        if (suggestedPrice.compareTo(minPrice) <= 0 || suggestedPrice.compareTo(maxPrice) >= 0) {
            throw new IllegalArgumentException("Suggested price must be strictly between min and max price.");
        }
    }


}
