package com.evdealer.ev_dealer_management.user.repository;

import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerHierarchyRepository
        extends JpaRepository<DealerHierarchy, Long> {
}
