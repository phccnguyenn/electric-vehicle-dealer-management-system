package com.evdealer.ev_dealer_management.warehouse.repository;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseCarRepository
        extends JpaRepository<WarehouseCar, Long> {

//    Optional<WarehouseCar> findByCarDetailAndDealer(CarDetail carDetail, User dealer);
//    Page<WarehouseCar> findAllByDealer(User dealer, Pageable pageable);

}
