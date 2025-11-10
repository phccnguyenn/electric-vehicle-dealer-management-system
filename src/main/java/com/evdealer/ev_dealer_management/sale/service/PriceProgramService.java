package com.evdealer.ev_dealer_management.sale.service;

import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.sale.model.PriceProgram;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramPostDto;
import com.evdealer.ev_dealer_management.sale.repository.PriceProgramRepository;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceProgramService {

    private final DealerHierarchyRepository dealerHierarchyRepository;
    private final PriceProgramRepository priceProgramRepository;

    public PriceProgramGetDto getById(Long id) {
        PriceProgram priceProgram = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));
        return PriceProgramGetDto.fromModel(priceProgram);
    }

    public List<PriceProgramGetDto> getByDealerHierarchy(Integer dealerLevel) {
        DealerHierarchy hierarchy = dealerHierarchyRepository.findByLevelType(dealerLevel)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));

        return priceProgramRepository.findAllByDealerHierarchy(hierarchy).stream()
                .map(PriceProgramGetDto::fromModel)
                .toList();
    }

    public PriceProgramGetDto createNewPriceProgram(PriceProgramPostDto priceProgramPostDto) {

        if (!dateRangeValidation(priceProgramPostDto.startDay(), priceProgramPostDto.endDay()))
            throw new IllegalArgumentException("Start date must be before end date.");

        DealerHierarchy dealerHierarchy = dealerHierarchyRepository.findByLevelType(priceProgramPostDto.dealerHierarchy())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));

        PriceProgram priceProgram = PriceProgram.builder()
                .dealerHierarchy(dealerHierarchy)
                .startDay(priceProgramPostDto.startDay())
                .endDay(priceProgramPostDto.endDay())
                .build();

        return PriceProgramGetDto.fromModel(priceProgramRepository.save(priceProgram));
    }

    public PriceProgramGetDto updatePartialById(Long id, PriceProgramPostDto dto) {
        PriceProgram existing = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));

        if (dto.startDay() != null && dto.endDay() != null && !dateRangeValidation(dto.startDay(), dto.endDay())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        if (dto.dealerHierarchy() != null) {
            DealerHierarchy dealerHierarchy = dealerHierarchyRepository.findByLevelType(dto.dealerHierarchy())
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));
            existing.setDealerHierarchy(dealerHierarchy);
        }

        if (dto.startDay() != null) {
            existing.setStartDay(dto.startDay());
        }

        if (dto.endDay() != null) {
            existing.setEndDay(dto.endDay());
        }

        return PriceProgramGetDto.fromModel(priceProgramRepository.save(existing));
    }

    public void deleteById(Long id) {
        PriceProgram priceProgram = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));
        priceProgramRepository.delete(priceProgram);
    }

    private boolean dateRangeValidation(OffsetDateTime startDate, OffsetDateTime endDate) {
        if (startDate == null || endDate == null)
            return false;
        return startDate.isBefore(endDate);
    }

}
