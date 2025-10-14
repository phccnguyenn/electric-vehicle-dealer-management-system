package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.*;
import com.evdealer.ev_dealer_management.car.model.dto.car.*;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarImageService carImageService;
    private final ColorService colorService;
    private final DimensionService dimensionService;
    private final PerformanceService performanceService;
    private final CategoryService categoryService;
    private final CarRepository carRepository;

    public CarListGetDto getAllCars(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Car> carPage = carRepository.findAll(pageable);

        return toCarListGetDto(carPage);
    }

    public CarDetailGetDto getDetailCarById(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_NOT_FOUND, carId));

        return CarDetailGetDto.fromModel(car);
    }

    @Transactional
    public CarDetailGetDto createCar(CarPostDto carPostDto) {

        Car car = new Car();
        car.setCarName(carPostDto.carName());
        car.setDriveType(carPostDto.driveType());
        car.setYear(carPostDto.year());
        car.setPrice(carPostDto.price());

        // Set car performance (1:1)
        Performance performance = performanceService.createPerformance(carPostDto.performancePostDto());
        car.setPerformance(performance);

        // Set dimension (1:1)
        Dimension dimension = dimensionService.createDimension(carPostDto.dimensionPostDto());
        car.setDimension(dimension);

        // Save car (in Database) to get car id
        Car savedCar = carRepository.save(car);

        // Set car color (color 1 : N car)
        Color color = colorService.getOrCreateColor(carPostDto.colorPostDto());
        savedCar.setPrice(setExtraCost(savedCar.getPrice(), color.getExtraCost()));
        savedCar.setColor(color);
        colorService.updateColor(color.getId(), savedCar);

        // Set car category (category 1 : N car)
        Category category = categoryService.addCarToCategory(carPostDto.categoryId(), savedCar);
        savedCar.setCategory(category);

        return CarDetailGetDto.fromModel(carRepository.save(savedCar));
    }

    public CarDetailGetDto uploadImagesForCar(Long carId, MultipartFile[] images) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_NOT_FOUND, carId));

        // Set List<Image>
        List<CarImage> carImages = carImageService.uploadImageToCar(car, images);
        carImages.forEach(carImage -> car.getCarImages().add(carImage));

        return CarDetailGetDto.fromModel(carRepository.save(car));
    }

    public void updatePartialCarByCarId(Long carId, CarPatchDto carPatchDto) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_NOT_FOUND, carId));

        if (carPatchDto.carName() != null && !car.getCarName().equals(carPatchDto.carName())) {
            car.setCarName(carPatchDto.carName());
        }

        if (carPatchDto.price() != null && car.getPrice().compareTo(carPatchDto.price()) != 0) {
            car.setPrice(carPatchDto.price());
        }

        if (carPatchDto.driveType() != null && car.getDriveType() != carPatchDto.driveType()) {
            car.setDriveType(carPatchDto.driveType());
        }

        if (carPatchDto.year() > 0 && car.getYear() != carPatchDto.year()) {
            car.setYear(carPatchDto.year());
        }

        carRepository.save(car);
    }

    private BigDecimal setExtraCost(BigDecimal currentPrice, BigDecimal extraCost) {

        if (extraCost.equals(BigDecimal.ZERO))
            return currentPrice;

        return currentPrice.add(extraCost);
    }

    private CarListGetDto toCarListGetDto(Page<Car> carPage) {

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
