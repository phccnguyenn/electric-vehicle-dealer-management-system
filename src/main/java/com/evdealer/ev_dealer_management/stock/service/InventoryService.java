package com.evdealer.ev_dealer_management.stock.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.service.CarService;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.stock.model.Inventory;
import com.evdealer.ev_dealer_management.stock.model.dto.InventoryDetailsGetDto;
import com.evdealer.ev_dealer_management.stock.model.dto.InventoryListDto;
import com.evdealer.ev_dealer_management.stock.model.dto.InventoryPostDto;
import com.evdealer.ev_dealer_management.stock.model.dto.InventoryUpdateDto;
import com.evdealer.ev_dealer_management.stock.model.enumeration.InventoryStatus;
import com.evdealer.ev_dealer_management.stock.repository.InventoryRepository;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ManufacturerService manufacturerService;
    private final CarService carService;
    private final InventoryRepository inventoryRepository;

    public InventoryListDto getDealerInventoryById(Long dealerId,
                                                   int pageNo,
                                                   int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        User dealer = manufacturerService.getDealerByDealerId(dealerId);
        Page<Inventory> inventoryPage = inventoryRepository.findAllByDealer(dealer, pageable);

        List<InventoryDetailsGetDto> inventories = inventoryPage.getContent()
                .stream()
                .map(InventoryDetailsGetDto::fromModel)
                .toList();

        return new InventoryListDto(
                inventories,
                inventoryPage.getNumber(),
                inventoryPage.getSize(),
                (int) inventoryPage.getTotalElements(),
                inventoryPage.getTotalPages(),
                inventoryPage.isLast()
        );
    }

    public InventoryDetailsGetDto createDealerInventory(Long dealerId, String phoneNumber,
                                                       InventoryPostDto inventoryPostDto) {

        User dealer = (dealerId != null)
                ? manufacturerService.getDealerByDealerId(dealerId)
                : manufacturerService.getDealerByDealerPhone(phoneNumber);

        if (dealer == null)
            throw new IllegalArgumentException("Must provide dealerId or phone");

        if (!dealer.getRole().equals(RoleType.DEALER_MANAGER))
            throw new RuntimeException("Only dealer users can have inventories");

        return InventoryDetailsGetDto.fromModel(createInventory(dealer, inventoryPostDto));
    }

    public void modifyCarInventoryByDealer(Long carId, InventoryUpdateDto inventoryUpdateDto) {

        Car car = carService.getCarById(carId);
        User currentDealer = manufacturerService.getCurrentUser();
        Inventory inventory = inventoryRepository.findByCarAndDealer(car, currentDealer)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.INVENTORY_WITH_CAR_AND_DEALER_NOT_FOUND));

        if (!inventoryUpdateDto.quantity().equals(inventory.getQuantity())
                && inventoryUpdateDto.quantity() != null) {
            inventory.setQuantity(inventoryUpdateDto.quantity());
            inventory.setAvailableQuantity(inventory.getQuantity() - inventory.getReservedQuantity());
        }

        if (!inventoryUpdateDto.status().equals(inventory.getStatus())
                && inventoryUpdateDto.status() != null)
            inventory.setStatus(inventoryUpdateDto.status());

        if (!inventoryUpdateDto.notes().equals(inventory.getNotes())
                && inventoryUpdateDto.notes() != null)
            inventory.setNotes(inventoryUpdateDto.notes());

    }

    private Inventory createInventory(User dealer, InventoryPostDto inventoryPostDto) {

        Car currentCar = carService.getCarById(inventoryPostDto.carId());

        Integer reservedQuantity = (inventoryPostDto.reservedQuantity() == null)
                ? 0
                : inventoryPostDto.reservedQuantity();
        InventoryStatus inventoryStatus = (inventoryPostDto.inventoryStatus() == null)
                ? InventoryStatus.IN_STOCK
                : inventoryPostDto.inventoryStatus();


        Inventory inventory = Inventory.builder()
                .dealer(dealer)
                .car(currentCar)
                .quantity(inventoryPostDto.quantity())
                .reservedQuantity(reservedQuantity)
                .availableQuantity(inventoryPostDto.quantity() - reservedQuantity)
                .status(inventoryStatus)
                .notes(inventoryPostDto.note())
                .build();

        return inventoryRepository.save(inventory);
    }

}
