package com.evdealer.ev_dealer_management.sale.service;

import com.evdealer.ev_dealer_management.car.service.CarModelService;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.sale.model.PriceProgram;
import com.evdealer.ev_dealer_management.sale.model.ProgramDetail;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramListDto;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramPostDto;
import com.evdealer.ev_dealer_management.sale.repository.PriceProgramRepository;
import com.evdealer.ev_dealer_management.sale.repository.ProgramDetailRepository;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceProgramService {

    private final DealerService dealerService;
    private final DealerHierarchyRepository dealerHierarchyRepository;
    private final PriceProgramRepository priceProgramRepository;
    private final ProgramDetailRepository programDetailRepository;
    private final CarModelService carModelService;

    public PriceProgramListDto getAllPriceProgram(int pageNo, int pageSize) {
        Pageable priceProgramPageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "effectiveDate"));
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

    public List<PriceProgramGetDto> getCurrentAndUpcomingPriceProgram() {
        OffsetDateTime now = OffsetDateTime.now();
        List<PriceProgram> programs = priceProgramRepository.findCurrentAndUpcomingPricePrograms(now);

        return programs.stream().map(PriceProgramGetDto::fromModel).toList();
    }

    public PriceProgramGetDto getById(Long id) {
        PriceProgram priceProgram = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));
        return PriceProgramGetDto.fromModel(priceProgram);
    }

//    public List<PriceProgramGetDto> getByDealerHierarchy(Integer dealerLevel) {
//
//        // The reason of error for price program
//        if (dealerService.getCurrentUser().getRole().equals(RoleType.DEALER_STAFF)) {
//            dealerLevel = dealerService.getCurrentUser().getDealerInfo().getDealerHierarchy().getLevelType();
//        }
//
//        DealerHierarchy hierarchy = dealerHierarchyRepository.findByLevelType(dealerLevel)
//                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.DEALER_HIERARCHY_NOT_FOUND));
//
//        return priceProgramRepository.findAllByDealerHierarchy(hierarchy).stream()
//                .map(PriceProgramGetDto::fromModel)
//                .toList();
//    }

    public PriceProgramGetDto createNewPriceProgram(PriceProgramPostDto priceProgramPostDto) {

        if (!dateRangeValidation(priceProgramPostDto.startDay()))
            throw new IllegalArgumentException("Start date must be after now.");

        PriceProgram priceProgram = PriceProgram.builder()
                .programName(priceProgramPostDto.programName())
                .effectiveDate(priceProgramPostDto.startDay())
                .isActive(priceProgramPostDto.isActive())
                .build();

        return PriceProgramGetDto.fromModel(priceProgramRepository.save(priceProgram));
    }

    public PriceProgramGetDto updatePartialById(Long id, PriceProgramPostDto dto) {
        PriceProgram existing = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));

        if (!dateRangeValidation(dto.startDay()))
            throw new IllegalArgumentException("Start date must be after now.");
        existing.setEffectiveDate(dto.startDay());

        if (dto.programName() != null && !dto.programName().equals(existing.getProgramName()))
            existing.setProgramName(dto.programName());

        if (dto.isActive() != null) {
            existing.setActive(dto.isActive());
        }

        return PriceProgramGetDto.fromModel(priceProgramRepository.save(existing));
    }

    public void deleteById(Long id) {
        PriceProgram priceProgram = priceProgramRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.PRICE_PROGRAM_NOT_FOUND));
        priceProgramRepository.delete(priceProgram);
    }

    private boolean dateRangeValidation(OffsetDateTime startDate) {
        if (startDate == null)
            return false;
        return startDate.isAfter(OffsetDateTime.now());
    }

}
