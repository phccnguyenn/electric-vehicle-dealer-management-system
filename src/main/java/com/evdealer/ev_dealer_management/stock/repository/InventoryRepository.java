package com.evdealer.ev_dealer_management.stock.repository;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.stock.model.Inventory;
import com.evdealer.ev_dealer_management.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByCarAndDealer(Car car, User dealer);
    Page<Inventory> findAllByDealer(User dealer, Pageable pageable);

}
