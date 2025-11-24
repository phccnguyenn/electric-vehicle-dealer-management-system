package com.evdealer.ev_dealer_management.user.repository;

import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerInfoRepository extends JpaRepository<DealerInfo, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE u.dealerInfo = :dealerInfo " +
            "AND u.role IN :roles")
    Page<User> findAllEmployeesByDealerInfoAndRole(
            DealerInfo dealerInfo,
            List<RoleType> roles,
            Pageable pageable
    );

}
