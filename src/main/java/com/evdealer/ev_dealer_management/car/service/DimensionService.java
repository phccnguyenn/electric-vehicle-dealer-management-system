package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Dimension;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionPostDto;
import com.evdealer.ev_dealer_management.car.repository.DimensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DimensionService {

    private final DimensionRepository dimensionRepository;

    public Dimension createDimension(DimensionPostDto dimensionPostDto) {

        Dimension dimension = Dimension.builder()
                .seatNumber(dimensionPostDto.seatNumber())
                .weightLbs(dimensionPostDto.weightLbs())
                .groundClearanceIn(dimensionPostDto.groundClearanceIn())
                .widthFoldedIn(dimensionPostDto.widthFoldedIn())
                .widthExtendedIn(dimensionPostDto.widthExtendedIn())
                .heightIn(dimensionPostDto.heightIn())
                .lengthMm(dimensionPostDto.lengthMm())
                .lengthIn(dimensionPostDto.lengthIn())
                .wheelsSizeCm(dimensionPostDto.wheelsSizeCm())
                .build();

        return dimensionRepository.save(dimension);
    }
}
