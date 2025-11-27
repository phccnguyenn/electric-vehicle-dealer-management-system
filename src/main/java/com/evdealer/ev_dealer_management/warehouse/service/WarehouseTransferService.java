package com.evdealer.ev_dealer_management.warehouse.service;

import com.evdealer.ev_dealer_management.warehouse.model.WarehouseTransfer;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import com.evdealer.ev_dealer_management.warehouse.repository.WarehouseTransferRepository;
import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseTransferService {

    private final WarehouseTransferRepository warehouseTransferRepository;
    private final CarDetailRepository carDetailRepository;
    private final OrderRepository orderRepository;

    /**
     * Ghi nhận chuyển trạng thái kho cho 1 xe trong đơn hàng
     */
    @Transactional
    public void logTransfer(Long orderId, Long carId, WarehouseCarStatus newStatus, String note) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        CarDetail car = carDetailRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Tạo log mới
        WarehouseTransfer transfer = WarehouseTransfer.builder()
                .order(order)
                .car(car)
                .warehouseCarStatus(newStatus)
                .note(note)
                .build();

        warehouseTransferRepository.save(transfer);
    }

    /**
     * Lấy toàn bộ log chuyển trạng thái liên quan đến 1 đơn hàng
     */
    public List<WarehouseTransfer> getTransfersByOrder(Long orderId) {
        return warehouseTransferRepository.findByOrderIdOrderByCreatedOnAsc(orderId);
    }
}
