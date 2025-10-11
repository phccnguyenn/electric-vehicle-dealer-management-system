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

    public Dimension createDimension(DimensionPostDto dimensionPostDto, Long carId) {

        Dimension dimension = new Dimension();
        dimension.setLengthMm(dimensionPostDto.lengthMm());
        dimension.setLengthIn(dimensionPostDto.lengthIn());
        dimension.setWeightLbs(dimensionPostDto.weightLbs());
        dimension.setGroundClearanceIn(dimensionPostDto.groundClearanceIn());
        dimension.setWidthFoldedIn(dimensionPostDto.widthFoldedIn());
        dimension.setWidthExtendedIn(dimensionPostDto.widthExtendedIn());
        dimension.setHeightIn(dimensionPostDto.heightIn());
        dimension.setWheelsSizeCm(dimensionPostDto.wheelsSizeCm());

        return dimensionRepository.save(dimension);
    }
}
