package com.evdealer.ev_dealer_management.car.repository;

import com.evdealer.ev_dealer_management.car.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
}
