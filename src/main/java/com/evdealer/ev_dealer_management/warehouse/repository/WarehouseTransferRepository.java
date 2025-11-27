package com.evdealer.ev_dealer_management.warehouse.repository;

import com.evdealer.ev_dealer_management.warehouse.model.WarehouseTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer, Long> {
    //List<WarehouseTransfer> findByCarIdOrderByCreatedOnAsc(Long carId);
    //List<WarehouseTransfer> findByOrderIdOrderByCreatedOnAsc(Long orderId);
    List<WarehouseTransfer> findByToLocation(String toLocation);
}
