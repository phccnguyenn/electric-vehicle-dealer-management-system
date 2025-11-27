package com.evdealer.ev_dealer_management.sale.service;

import com.evdealer.ev_dealer_management.car.model.CarModel;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramDetailService {

    private final CarModelService carModelService;
    private final PriceProgramRepository priceProgramRepository;
    private final ProgramDetailRepository programDetailRepository;

    public ProgramDetailGetDto addDetailForSpecPriceProgram(Long priceProgramId,
                                                             boolean isAutoFilling,
                                                             ProgramDetailPostDto programDetailPostDto) {

        validatePriceRange(programDetailPostDto.minPrice(), programDetailPostDto.suggestedPrice(), programDetailPostDto.maxPrice());

        PriceProgram priceProgram = priceProgramRepository.findById(priceProgramId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND, priceProgramId));

        CarModel carModel = carModelService.getCarModelById(programDetailPostDto.carModelId(), CarModel.class);

        boolean exists = priceProgram.getProgramDetails().stream()
                .anyMatch(detail ->
                        detail.getCarModel().getId().equals(programDetailPostDto.carModelId()) &&
                                detail.isSpecialColor() == programDetailPostDto.isSpecialColor()
                );

        if (exists) {
            throw new RuntimeException("Program detail already exists for this model and color");
        }
        
        ProgramDetail programDetail = ProgramDetail.builder()
                .carModel(carModel)
                .isSpecialColor(programDetailPostDto.isSpecialColor())
                .minPrice(programDetailPostDto.minPrice())
                .suggestedPrice(programDetailPostDto.suggestedPrice())
                .maxPrice(programDetailPostDto.maxPrice())
                .priceProgram(priceProgram)
                .build();
        ProgramDetail savedProgramDetail = programDetailRepository.save(programDetail);

        priceProgram.getProgramDetails().add(savedProgramDetail);
        priceProgramRepository.save(priceProgram);

        if (isAutoFilling) {
            autoFillMissingDetailPrograms(priceProgramId);
        }

        return ProgramDetailGetDto.fromModel(savedProgramDetail);
    }

    @Transactional
    public void autoFillMissingDetailPrograms(Long newProgramId) {
        // 1. Lấy chương trình mới
        PriceProgram newProgram = priceProgramRepository.findById(newProgramId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND, newProgramId));

        // 2. Lấy chương trình cũ gần nhất trước effectiveDate của chương trình mới
        PriceProgram lastProgram = priceProgramRepository
                .findTopByEffectiveDateBeforeOrderByEffectiveDateDesc(newProgram.getEffectiveDate())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND, newProgramId));

        // 3. Lấy tất cả ProgramDetail của chương trình cũ
        List<ProgramDetail> oldDetails = programDetailRepository.findByPriceProgramId(lastProgram.getId());

        // 4. Lấy tất cả combination (carModelId + isSpecialColor) đã có trong chương trình mới
        Set<String> existingCombinations = newProgram.getProgramDetails().stream()
                .map(d -> d.getCarModel().getId() + "-" + (d.isSpecialColor() ? 1 : 0))
                .collect(Collectors.toSet());

        // 5. Lọc ra những chi tiết chưa có trong chương trình mới
        List<ProgramDetail> clones = oldDetails.stream()
                .filter(d -> !existingCombinations.contains(d.getCarModel().getId() + "-" + (d.isSpecialColor() ? 1 : 0)))
                .map(d -> {
                    ProgramDetail clone = new ProgramDetail();
                    clone.setPriceProgram(newProgram);
                    clone.setCarModel(d.getCarModel());
                    clone.setMinPrice(d.getMinPrice());
                    clone.setSuggestedPrice(d.getSuggestedPrice());
                    clone.setMaxPrice(d.getMaxPrice());
                    clone.setSpecialColor(d.isSpecialColor());
                    return clone;
                })
                .toList();

        // 6. Lưu batch
        programDetailRepository.saveAll(clones);

        // 7. Cập nhật relation
        newProgram.getProgramDetails().addAll(clones);
        priceProgramRepository.save(newProgram);
    }



    public ProgramDetailGetDto updateProgramDetail(Long detailId, ProgramDetailPostDto updateDto) {

        ProgramDetail existingDetail = programDetailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PROGRAM_DETAIL_NOT_FOUND, detailId));

        if (updateDto.carModelId() != null) {
            CarModel carModel = carModelService.getCarModelById(updateDto.carModelId(), CarModel.class);
            existingDetail.setCarModel(carModel);
        }

        validatePriceRange(
                updateDto.minPrice(),
                updateDto.suggestedPrice(),
                updateDto.maxPrice()
        );

        if (existingDetail.getMinPrice().compareTo(updateDto.minPrice()) != 0) {
            existingDetail.setMinPrice(updateDto.minPrice());
        }

        if (existingDetail.getSuggestedPrice().compareTo(updateDto.suggestedPrice()) != 0) {
            existingDetail.setSuggestedPrice(updateDto.suggestedPrice());
        }

        if (existingDetail.getMaxPrice().compareTo(updateDto.maxPrice()) != 0) {
            existingDetail.setMaxPrice(updateDto.maxPrice());
        }

        // special color does not include BLACK && WHILE
        if (existingDetail.isSpecialColor() != updateDto.isSpecialColor())
            existingDetail.setSpecialColor(updateDto.isSpecialColor());

        ProgramDetail updated = programDetailRepository.save(existingDetail);
        return ProgramDetailGetDto.fromModel(updated);
    }

    public void deleteProgramDetail(Long detailId) {
        ProgramDetail programDetail = programDetailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PROGRAM_DETAIL_NOT_FOUND, detailId));

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
