//package com.evdealer.ev_dealer_management.warehouse.service;
//
//import com.evdealer.ev_dealer_management.car.model.CarDetail;
//import com.evdealer.ev_dealer_management.car.service.CarDetailService;
//import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
//import com.evdealer.ev_dealer_management.common.utils.Constants;
//import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarDetailsGetDto;
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarListDto;
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarPostDto;
//import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarUpdateDto;
//import com.evdealer.ev_dealer_management.warehouse.model.enumeration.InventoryStatus;
//import com.evdealer.ev_dealer_management.warehouse.repository.WarehouseCarRepository;
//import com.evdealer.ev_dealer_management.user.model.User;
//import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
//import com.evdealer.ev_dealer_management.user.service.ManufacturerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class InventoryService {
//
//    private final ManufacturerService manufacturerService;
//    private final CarDetailService carDetailService;
//    private final WarehouseCarRepository warehouseCarRepository;
//
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
//
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
//        return WarehouseCarDetailsGetDto.fromModel(createInventory(dealer, warehouseCarPostDto));
//    }
//
//    public void modifyCarInventoryByDealer(Long carId, WarehouseCarUpdateDto warehouseCarUpdateDto) {
//
//        CarDetail carDetail = carDetailService.getCarById(carId);
//        User currentDealer = manufacturerService.getCurrentUser();
//        WarehouseCar warehouseCar = warehouseCarRepository.findByCarDetailAndDealer(carDetail, currentDealer)
//                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.INVENTORY_WITH_CAR_AND_DEALER_NOT_FOUND));
//
//        if (!warehouseCarUpdateDto.quantity().equals(warehouseCar.getQuantity())
//                && warehouseCarUpdateDto.quantity() != null) {
//            warehouseCar.setQuantity(warehouseCarUpdateDto.quantity());
//            warehouseCar.setAvailableQuantity(warehouseCar.getQuantity() - warehouseCar.getReservedQuantity());
//        }
//
//        if (!warehouseCarUpdateDto.status().equals(warehouseCar.getStatus())
//                && warehouseCarUpdateDto.status() != null)
//            warehouseCar.setStatus(warehouseCarUpdateDto.status());
//
//        if (!warehouseCarUpdateDto.notes().equals(warehouseCar.getNotes())
//                && warehouseCarUpdateDto.notes() != null)
//            warehouseCar.setNotes(warehouseCarUpdateDto.notes());
//
//    }
//
//    private WarehouseCar createInventory(User dealer, WarehouseCarPostDto warehouseCarPostDto) {
//
//        CarDetail currentCarDetail = carDetailService.getCarById(warehouseCarPostDto.carId());
//
//        Integer reservedQuantity = (warehouseCarPostDto.reservedQuantity() == null)
//                ? 0
//                : warehouseCarPostDto.reservedQuantity();
//        InventoryStatus inventoryStatus = (warehouseCarPostDto.inventoryStatus() == null)
//                ? InventoryStatus.IN_STOCK
//                : warehouseCarPostDto.inventoryStatus();
//
//
//        WarehouseCar warehouseCar = WarehouseCar.builder()
//                .carDetail(currentCarDetail)
//                .quantity(warehouseCarPostDto.quantity())
//                .build();
//
//        return warehouseCarRepository.save(warehouseCar);
//    }
//
//}
