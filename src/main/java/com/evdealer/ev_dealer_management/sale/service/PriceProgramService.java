package com.evdealer.ev_dealer_management.sale.service;

import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.sale.model.PriceProgram;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramListDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramPostDto;
import com.evdealer.ev_dealer_management.sale.repository.PriceProgramRepository;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceProgramService {

    private final DealerService dealerService;
    private final DealerHierarchyRepository dealerHierarchyRepository;
    private final PriceProgramRepository priceProgramRepository;

    public PriceProgramListDto getAllPriceProgram(int pageNo, int pageSize) {
        Pageable priceProgramPageable = PageRequest.of(pageNo, pageSize);
        Page<PriceProgram> priceProgramPage = priceProgramRepository.findAll(priceProgramPageable);

        List<PriceProgramGetDto> priceProgramGetDtoList = priceProgramPage.getContent()
                .stream()
                .map(PriceProgramGetDto::fromModel)
                .toList();

        return new PriceProgramListDto(
                priceProgramGetDtoList,
                priceProgramPage.getNumber(),
                priceProgramPage.getSize(),
                (int) priceProgramPage.getTotalElements(),
                priceProgramPage.getTotalPages(),
                priceProgramPage.isLast()
        );
    }

    public PriceProgramGetDto getById(Long id) {
        PriceProgram priceProgram = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));
        return PriceProgramGetDto.fromModel(priceProgram);
    }

    public List<PriceProgramGetDto> getByDealerHierarchy(Integer dealerLevel) {

        // The reason of error for price program
        if (dealerService.getCurrentUser().getRole().equals(RoleType.DEALER_STAFF)) {
            dealerLevel = dealerService.getCurrentUser().getParent().getDealerHierarchy().getLevelType();
        }

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
