package com.evdealer.ev_dealer_management.warehouse.service;

import com.evdealer.ev_dealer_management.warehouse.model.Warehouse;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseTransfer;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseTransferDto;
import com.evdealer.ev_dealer_management.warehouse.repository.WarehouseTransferRepository;
import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseTransferService {

    private final WarehouseTransferRepository warehouseTransferRepository;
    private final CarDetailRepository carDetailRepository;

    /**
     * Ghi nhận chuyển trạng thái kho cho 1 xe trong đơn hàng
     */
    @Transactional
    public void logTransfer (Warehouse warehouse, Long carId, String from, String to, String note) {

        CarDetail car = carDetailRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Tạo log mới
        WarehouseTransfer transfer = WarehouseTransfer.builder()
                .warehouse(warehouse)
                .car(car)
                .fromLocation(from)
                .toLocation(to)
                .note(note)
                .build();

        warehouseTransferRepository.save(transfer);
    }

    public List<WarehouseTransferDto> getWarehouseHistory() {
        List<WarehouseTransfer> transfers = warehouseTransferRepository.findAllByOrderByCreatedOnAsc();

        return transfers.stream()
                .map(WarehouseTransferDto::fromModel)
                .toList();
    }

    /**
     * Lấy toàn bộ log chuyển trạng thái liên quan đến 1 đơn hàng
     */
//    public List<WarehouseTransfer> getTransfersByOrder(Long orderId) {
//        return warehouseTransferRepository.findByOrderIdOrderByCreatedOnAsc(orderId);
//    }
}
