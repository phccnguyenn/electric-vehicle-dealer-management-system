package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.Dimension;
import com.evdealer.ev_dealer_management.car.model.dto.dimension.DimensionPostDto;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import com.evdealer.ev_dealer_management.car.repository.DimensionRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DimensionService {

    private final DimensionRepository dimensionRepository;
    private final CarRepository carRepository;

    public DimensionPostDto createDimension(DimensionPostDto dimensionPostDto,Long carId) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_NOT_FOUND, carId));


        Dimension dimension = dimensionRepository.findByCarId(carId)
                                                .orElse(new Dimension());
        dimension.setCar(car);
        dimension.setWeightLbs(dimensionPostDto.weightLbs());
        dimension.setGroundClearanceIn(dimensionPostDto.groundClearanceIn());
        dimension.setWidthFoldedIn(dimensionPostDto.widthFoldedIn());
        dimension.setWidthExtendedIn(dimensionPostDto.widthExtendedIn());
        dimension.setHeightIn(dimensionPostDto.heightIn());
        dimension.setLengthIn(dimensionPostDto.lengthIn());
        dimension.setWheelsSizeCm(dimensionPostDto.wheelsSizeCm());


        dimension = dimensionRepository.save(dimension);
        return null ;




    }
}
