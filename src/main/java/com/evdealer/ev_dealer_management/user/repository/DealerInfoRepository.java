package com.evdealer.ev_dealer_management.user.repository;

import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerInfoRepository extends JpaRepository<DealerInfo, Long> {
}
