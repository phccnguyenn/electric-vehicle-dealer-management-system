package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.*;
import com.evdealer.ev_dealer_management.car.model.dto.details.*;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.car.repository.CarModelRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarUpdateDto;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import com.evdealer.ev_dealer_management.warehouse.service.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CarDetailService {

    private final WarehouseService warehouseService;
    private final DimensionService dimensionService;
    private final PerformanceService performanceService;
    private final CarImageService carImageService;
    private final CarModelService carModelService;
    private final CarDetailRepository carDetailRepository;
    private final CarModelRepository carModelRepository;

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

    public CarDetailGetDto getOneRandomCarDetail(Long carModelId, String color, CarStatus carDetailStatus) {
        String carStatusStr = (carDetailStatus != null)
                ? carDetailStatus.toString()
                : "";

        List<CarDetail> carDetails = carDetailRepository
                .getOneRandomCarDetailByOptionalParameter(
                        carModelId,
                        color,
                        carStatusStr
                );

        CarDetail carDetail = null;
        if (carDetails.isEmpty())
            throw new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND);
        else {
            carDetail = carDetails.get(new Random().nextInt(carDetails.size()));
        }

        // Due to random for creating order, decrease one unit in warehouse
        WarehouseCarUpdateDto warehouseCarUpdateDto = new WarehouseCarUpdateDto(
                carDetail.getCarModel().getId(),
                carDetail.getCarModel().getCarDetails().isEmpty() ? 0 : carDetail.getCarModel().getCarDetails().size() - 1,
                carDetail.getCarModel().getCarDetails().isEmpty() ? WarehouseCarStatus.OUT_OF_STOCK : WarehouseCarStatus.IN_STOCK
        );
        warehouseService.updateWarehouseCar(carDetail.getCarModel().getId(), warehouseCarUpdateDto);

        return CarDetailGetDto.fromModel(carDetail);
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
        carDetail.setVinNumber(carDetailPostDto.vinNumber());
        carDetail.setEngineNumber(carDetailPostDto.engineNumber());

        // Set carDetail performance (1:1)
        Performance performance = performanceService.createPerformance(carDetailPostDto.performancePostDto());
        carDetail.setPerformance(performance);

        // Set dimension (1:1)
        Dimension dimension = dimensionService.createDimension(carDetailPostDto.dimensionPostDto());
        carDetail.setDimension(dimension);

        // Save carDetail (in Database) to get carDetail id
        CarDetail savedCarDetail = carDetailRepository.save(carDetail);

        // Set carDetail model (car_model 1 : N car_detail)
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

    @Transactional
    public void updatePartialCarByCarId(Long carId, CarPatchDto carPatchDto) {

        CarDetail carDetail = carDetailRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, carId));

        // Update carModelId
        if (carPatchDto.carModelId() != null &&
                (carDetail.getCarModel() == null || !carPatchDto.carModelId().equals(carDetail.getCarModel().getId()))) {

            // Lấy CarModel mới
            CarModel newCarModel = carModelRepository.findById(carPatchDto.carModelId())
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, carPatchDto.carModelId()));

            // Xóa CarDetail khỏi CarModel cũ
            CarModel oldCarModel = carDetail.getCarModel();
            if (oldCarModel != null) {
                oldCarModel.getCarDetails().remove(carDetail);
                carModelRepository.save(oldCarModel); // phải save thủ công
            }

            // Thêm CarDetail vào CarModel mới
            newCarModel.getCarDetails().add(carDetail);
            carModelRepository.save(newCarModel); // phải save thủ công

            // Cập nhật reference CarDetail
            carDetail.setCarModel(newCarModel);
        }

        // Các field khác
        if (carPatchDto.carName() != null && !carPatchDto.carName().equals(carDetail.getCarName())) {
            carDetail.setCarName(carPatchDto.carName());
        }
        if (carPatchDto.carStatus() != null && carPatchDto.carStatus() != carDetail.getCarStatus()) {
            carDetail.setCarStatus(carPatchDto.carStatus());
        }
        if (carPatchDto.color() != null && !carPatchDto.color().equals(carDetail.getColor())) {
            carDetail.setColor(carPatchDto.color());
        }

        carDetailRepository.save(carDetail); // save CarDetail
    }

    public void deleteCarDetail(Long carId) {
        CarDetail carDetail = carDetailRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, carId));

        carDetailRepository.delete(carDetail);
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
