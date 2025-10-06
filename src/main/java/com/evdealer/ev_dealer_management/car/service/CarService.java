    package com.evdealer.ev_dealer_management.car.service;

    import com.evdealer.ev_dealer_management.car.model.Car;
    import com.evdealer.ev_dealer_management.car.model.Category;
    import com.evdealer.ev_dealer_management.car.model.Color;
    import com.evdealer.ev_dealer_management.car.model.Performance;
    import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
    import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
    import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformanceDetailGetDto;
    import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
    import com.evdealer.ev_dealer_management.car.repository.CarRepository;
    import com.evdealer.ev_dealer_management.car.repository.CategoryRepository;
    import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
    import com.evdealer.ev_dealer_management.utils.Constants;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    @Service
    @Transactional
    @RequiredArgsConstructor
    public class CarService {

        private final ColorService colorService;
        private final PerformanceService performanceService;
        private final CategoryRepository categoryRepository;
        private final CarRepository carRepository;

        // Lưu car vào database <-> JPARepository
        public CarDetailGetDto createCar(CarPostDto carPostDto) {

            Car car = new Car();
            car.setCarName(carPostDto.carName());
            car.setDriveType(carPostDto.driveType());
            car.setSeatNumber(carPostDto.seatNumber());
            car.setYear(carPostDto.year());
            Car savedCar = carRepository.save(car);

            // Set car category
            setCategory(carPostDto.categoryId(), savedCar);

            // Set car color
            Color color = colorService.createColor(carPostDto.colorPostDto(), savedCar);
            savedCar.setColor(color);

            // Set car performance
            PerformanceDetailGetDto p
                    = performanceService.createPerformance(savedCar.getId(), carPostDto.performancePostDto());

        }

        private void setCategory(Long categoryId, Car car) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CATEGORY_NOT_FOUND, categoryId));

            category.getCars().add(car);
            categoryRepository.save(category);
        }

    }
