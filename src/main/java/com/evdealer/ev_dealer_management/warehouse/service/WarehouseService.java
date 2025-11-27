package com.evdealer.ev_dealer_management.warehouse.service;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.service.CarModelService;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.warehouse.model.Warehouse;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarDetailsGetDto;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarListDto;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarPostDto;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarUpdateDto;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import com.evdealer.ev_dealer_management.warehouse.repository.WarehouseCarRepository;
import com.evdealer.ev_dealer_management.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final CarModelService carModelService;
    private final WarehouseCarRepository warehouseCarRepository;
    private final WarehouseRepository warehouseRepository;

//    public WarehouseCarListDto getDealerInventoryById(Long dealerId,
//                                                      int pageNo,
//                                                      int pageSize) {
//
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        User dealer = manufacturerService.getDealerByDealerId(dealerId);
//        Page<WarehouseCar> inventoryPage = warehouseCarRepository.findAllByDealer(dealer, pageable);
//
//        List<WarehouseCarDetailsGetDto> inventories = inventoryPage.getContent()
//                .stream()
//                .map(WarehouseCarDetailsGetDto::fromModel)
//                .toList();
//
//        return new WarehouseCarListDto(
//                inventories,
//                inventoryPage.getNumber(),
//                inventoryPage.getSize(),
//                (int) inventoryPage.getTotalElements(),
//                inventoryPage.getTotalPages(),
//                inventoryPage.isLast()
//        );
//    }

//    public WarehouseCarDetailsGetDto createDealerInventory(Long dealerId, String phoneNumber,
//                                                           WarehouseCarPostDto warehouseCarPostDto) {
//
//        User dealer = (dealerId != null)
//                ? manufacturerService.getDealerByDealerId(dealerId)
//                : manufacturerService.getDealerByDealerPhone(phoneNumber);
//
//        if (dealer == null)
//            throw new IllegalArgumentException("Must provide dealerId or phone");
//
//        if (!dealer.getRole().equals(RoleType.DEALER_MANAGER))
//            throw new RuntimeException("Only dealer users can have inventories");
//
//        return WarehouseCarDetailsGetDto.fromModel(createWarehouseWithCarDetail(dealer, warehouseCarPostDto));
//    }

    public WarehouseCarListDto getAllWarehouseCar(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<WarehouseCar> warehouseCarPage = warehouseCarRepository.findAll(pageable);

        List<WarehouseCarDetailsGetDto> warehouseCars
                = warehouseCarPage.getContent()
                                .stream()
                                .map(WarehouseCarDetailsGetDto::fromModel)
                                .toList();

        return new WarehouseCarListDto(
                warehouseCars,
                warehouseCarPage.getNumber(),
                warehouseCarPage.getSize(),
                (int) warehouseCarPage.getTotalElements(),
                warehouseCarPage.getTotalPages(),
                warehouseCarPage.isLast()
        );
    }

    public WarehouseCarListDto getAllWarehouseCarByWarehouseCarStatus(int pageNo, int pageSize,
                                                                      WarehouseCarStatus warehouseCarStatus) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<WarehouseCar> warehouseCarPage
                = warehouseCarRepository.findAllByWarehouseCarStatus(warehouseCarStatus, pageable);

        List<WarehouseCarDetailsGetDto> warehouseCars
                = warehouseCarPage.getContent()
                .stream()
                .map(WarehouseCarDetailsGetDto::fromModel)
                .toList();

        return new WarehouseCarListDto(
                warehouseCars,
                warehouseCarPage.getNumber(),
                warehouseCarPage.getSize(),
                (int) warehouseCarPage.getTotalElements(),
                warehouseCarPage.getTotalPages(),
                warehouseCarPage.isLast()
        );
    }

    public WarehouseCar updateWarehouseCar(Long warehouseCarId, WarehouseCarUpdateDto warehouseCarUpdateDto) {

        CarModel carModel = carModelService.getCarModelById(warehouseCarUpdateDto.carModelId(), CarModel.class);

        WarehouseCar warehouseCar = warehouseCarRepository.findById(warehouseCarId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.WAREHOUSE_CAR_NOT_FOUND));

        if (!warehouseCarUpdateDto.quantity().equals(warehouseCar.getQuantity())
                && warehouseCarUpdateDto.quantity() != null)
            warehouseCar.setQuantity(warehouseCarUpdateDto.quantity());

        if (!warehouseCarUpdateDto.warehouseCarStatus().equals(warehouseCar.getWarehouseCarStatus())
                && warehouseCarUpdateDto.warehouseCarStatus() != null)
            warehouseCar.setWarehouseCarStatus(warehouseCarUpdateDto.warehouseCarStatus());

        if (carModel != null && !warehouseCar.getCarModel().equals(carModel))
            warehouseCar.setCarModel(carModel);

        return warehouseCarRepository.save(warehouseCar);
    }

    public WarehouseCarDetailsGetDto createWarehouseWithCarModel(WarehouseCarPostDto warehouseCarPostDto) {

        Warehouse warehouse = warehouseRepository.findById(warehouseCarPostDto.warehouseId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.WAREHOUSE_NOT_FOUND));

        CarModel carModel = carModelService.getCarModelById(warehouseCarPostDto.carModelId(), CarModel.class);

        WarehouseCarStatus warehouseCarStatus = (warehouseCarPostDto.warehouseCarStatus() == null)
                ? WarehouseCarStatus.IN_STOCK
                : warehouseCarPostDto.warehouseCarStatus();


        WarehouseCar warehouseCar = WarehouseCar.builder()
                .warehouse(warehouse)
                .carModel(carModel)
                .quantity(warehouseCarPostDto.quantity())
                .warehouseCarStatus(warehouseCarStatus)
                .build();

        WarehouseCar savedWarehouseCar = warehouseCarRepository.save(warehouseCar);
        warehouse.getWarehouseCars().add(savedWarehouseCar);
        warehouseRepository.save(warehouse);

        return WarehouseCarDetailsGetDto.fromModel(warehouseCarRepository.save(savedWarehouseCar));
    }

    @Transactional
    public void seedCarModelInWarehouse() {

        if (warehouseCarRepository.count() > 0)
            return;

        for (long carModelId = 1; carModelId <= 5; carModelId++) {
            CarModel carModel = carModelService.getCarModelById(carModelId, CarModel.class);

            WarehouseCarPostDto dto = new WarehouseCarPostDto(
                    1L, // warehouseId
                    carModelId,
                    carModel.getCarDetails().size(),
                    carModel.getCarDetails().isEmpty() ? WarehouseCarStatus.OUT_OF_STOCK : WarehouseCarStatus.IN_STOCK
            );

            createWarehouseWithCarModel(dto);
        }
    }

}
