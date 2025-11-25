package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelInfoGetDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.repository.CarModelRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarModelService {

    private final CarModelRepository carModelRepository;

    public <T> T getCarModelById(Long carModelId, Class<T> type) {
        CarModel carModel = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, carModelId));

        if (type.equals(CarModel.class))
            return type.cast(carModel);
        else if (type.equals(CarModelGetDetailDto.class))
            return type.cast(CarModelGetDetailDto.fromModel(carModel));
        else
            throw new IllegalArgumentException("Unsupported return type: " + type);
    }

    public List<CarModelInfoGetDto> getAllCarModel() {
        return carModelRepository.findAll()
                .stream()
                .map(CarModelInfoGetDto::fromModel)
                .toList();
    }

    public List<Long> getAllCarModelIds() {
        return carModelRepository.findAll()
                .stream()
                .map(CarModel::getId)
                .toList();
    }

    public CarModelInfoGetDto getCarModelById(Long carModelId) {
        CarModel carModel = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, carModelId));

        return CarModelInfoGetDto.fromModel(carModel);
    }

    public List<CarModelInfoGetDto> getTrialCarModel() {
        return carModelRepository
                .findDistinctByCarDetails_CarStatus(CarStatus.TEST_DRIVE_ONLY)
                .stream()
                .map(CarModelInfoGetDto::fromModel)
                .toList();
    }

    public CarModelGetDetailDto addNewCarModel(String carModelName) {
        CarModel carModel = new CarModel();
        carModel.setCarModelName(carModelName);
        return CarModelGetDetailDto.fromModel(carModelRepository.save(carModel));
    }

    public CarModel addSpecsCarToCategory(Long carModelId, CarDetail carDetail) {
        CarModel carModel = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, carModelId));
        carModel.getCarDetails().add(carDetail);
        return carModelRepository.save(carModel);
    }

    public void removeCarModelById(Long carModelId) {
        CarModel carModel = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, carModelId));
        carModelRepository.delete(carModel);
    }

}
