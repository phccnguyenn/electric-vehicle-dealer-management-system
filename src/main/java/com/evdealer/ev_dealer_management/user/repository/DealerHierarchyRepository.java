package com.evdealer.ev_dealer_management.user.repository;

import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealerHierarchyRepository
        extends JpaRepository<DealerHierarchy, Long> {

    Optional<DealerHierarchy> findByLevelType(Integer levelType);

}
