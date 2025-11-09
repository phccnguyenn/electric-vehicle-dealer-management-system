package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.*;
import com.evdealer.ev_dealer_management.car.model.dto.details.*;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDetailService {

    private final DimensionService dimensionService;
    private final PerformanceService performanceService;
    private final CarImageService carImageService;
    private final CarModelService carModelService;
    private final CarDetailRepository carDetailRepository;

    public CarListGetDto getAllCars(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CarDetail> carPage = carDetailRepository.findAll(pageable);

        return toCarListGetDto(carPage);
    }

    public CarListGetDto getAllCarsByCarModelId(Long carModelId, int pageNo, int pageSize) {
        CarModel carModel = carModelService.getCarModelById(carModelId, CarModel.class);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CarDetail> carPage = carDetailRepository.findAllByCarModel(carModel, pageable);

        return toCarListGetDto(carPage);
    }

    public CarDetailGetDto getDetailCarById(Long carId) {
        CarDetail carDetail = carDetailRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, carId));

        return CarDetailGetDto.fromModel(carDetail);
    }

    public CarDetail getCarById(Long carId) {
        return carDetailRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, carId));
    }

    @Transactional
    public CarDetailGetDto createCar(CarDetailPostDto carDetailPostDto) {

        CarDetail carDetail = new CarDetail();
        carDetail.setCarName(carDetailPostDto.carName());
        carDetail.setCarStatus(carDetailPostDto.carStatus());
        carDetail.setColor(carDetailPostDto.color());

        // Set car performance (1:1)
        Performance performance = performanceService.createPerformance(carDetailPostDto.performancePostDto());
        carDetail.setPerformance(performance);

        // Set dimension (1:1)
        Dimension dimension = dimensionService.createDimension(carDetailPostDto.dimensionPostDto());
        carDetail.setDimension(dimension);

        // Save car (in Database) to get car id
        CarDetail savedCarDetail = carDetailRepository.save(carDetail);

        // Set car model (car_model 1 : N car_detail)
        CarModel carModel = carModelService.addSpecsCarToCategory(carDetailPostDto.carModelId(), savedCarDetail);
        savedCarDetail.setCarModel(carModel);

        return CarDetailGetDto.fromModel(carDetailRepository.save(savedCarDetail));
    }

    public CarDetailGetDto uploadImagesForCar(Long carId, MultipartFile[] images) {

        CarDetail carDetail = carDetailRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, carId));

        // Set List<Image>
        List<CarImage> carImages = carImageService.uploadImageToCar(carDetail, images);
        carImages.forEach(carImage -> carDetail.getCarImages().add(carImage));

        return CarDetailGetDto.fromModel(carDetailRepository.save(carDetail));
    }

    public void updatePartialCarByCarId(Long carId, CarPatchDto carPatchDto) {

        CarDetail carDetail = carDetailRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, carId));

        if (carPatchDto.carName() != null && !carDetail.getCarName().equals(carPatchDto.carName())) {
            carDetail.setCarName(carPatchDto.carName());
        }

        carDetailRepository.save(carDetail);
    }

    private CarListGetDto toCarListGetDto(Page<CarDetail> carPage) {

        List<CarInfoGetDto> carInfoGetDtos = carPage.getContent()
                .stream()
                .map(CarInfoGetDto::fromModel)
                .toList();

        return new CarListGetDto(
                carInfoGetDtos,
                carPage.getNumber(),
                carPage.getSize(),
                (int) carPage.getTotalElements(),
                carPage.getTotalPages(),
                carPage.isLast()
        );
    }
}
