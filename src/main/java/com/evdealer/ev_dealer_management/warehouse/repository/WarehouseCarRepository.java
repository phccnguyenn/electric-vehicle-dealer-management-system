package com.evdealer.ev_dealer_management.warehouse.repository;

import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WarehouseCarRepository
        extends JpaRepository<WarehouseCar, Long> {

    Page<WarehouseCar> findAllByWarehouseCarStatus(WarehouseCarStatus warehouseCarStatus, Pageable pageable);

}
