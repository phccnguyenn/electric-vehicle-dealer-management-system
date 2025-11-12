package com.evdealer.ev_dealer_management.warehouse.repository;

import com.evdealer.ev_dealer_management.warehouse.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
