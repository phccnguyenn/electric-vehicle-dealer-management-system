    package com.evdealer.ev_dealer_management.car.service;

    import com.evdealer.ev_dealer_management.car.model.*;
    import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
    import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
    import com.evdealer.ev_dealer_management.car.repository.CarRepository;
    import com.evdealer.ev_dealer_management.car.repository.CategoryRepository;
    import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
    import com.evdealer.ev_dealer_management.thumbnail.model.dto.MediaPostDto;
    import com.evdealer.ev_dealer_management.thumbnail.service.MediaService;
    import com.evdealer.ev_dealer_management.utils.Constants;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.math.BigDecimal;

    @Service
    @RequiredArgsConstructor
    public class CarService {

        private final MediaService mediaService;
        private final ColorService colorService;
        private final DimensionService dimensionService;
        private final PerformanceService performanceService;
        private final CategoryRepository categoryRepository;
        private final CarRepository carRepository;

        @Transactional
        public CarDetailGetDto createCar(CarPostDto carPostDto, MediaPostDto mediaPostDto) {

            Car car = new Car();
            car.setCarName(carPostDto.carName());
            car.setDriveType(carPostDto.driveType());
            car.setSeatNumber(carPostDto.seatNumber());
            car.setYear(carPostDto.year());
            car.setPrice(carPostDto.price());
            Car savedCar = carRepository.save(car);

            // Set car category (category 1 : N car)
            setCategory(carPostDto.categoryId(), savedCar);

            // Set car color (color N : 1 car)
            Color color = colorService.createColor(carPostDto.colorPostDto(), savedCar);
            savedCar.setColor(color);
            savedCar.setPrice(setExtraCost(savedCar.getPrice(), color.getExtraCost()));

            // Set car performance (1:1)
            Performance performance
                    = performanceService.createPerformance(savedCar.getId(), carPostDto.performancePostDto());
            savedCar.setPerformance(performance);

            // Set dimension (1:1)
            Dimension dimension = dimensionService.createDimension(carPostDto.dimensionPostDto(), savedCar.getId());
            savedCar.setDimension(dimension);

            // Set List<Image>
            mediaService.addCarImages(mediaPostDto);

            return CarDetailGetDto.fromModel(savedCar);
        }

        private void setCategory(Long categoryId, Car car) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CATEGORY_NOT_FOUND, categoryId));

            category.getCars().add(car);
            categoryRepository.save(category);
        }

        private BigDecimal setExtraCost(BigDecimal currentPrice, Float extraCost) {
            return currentPrice.add(BigDecimal.valueOf(extraCost.doubleValue()));
        }
    }
