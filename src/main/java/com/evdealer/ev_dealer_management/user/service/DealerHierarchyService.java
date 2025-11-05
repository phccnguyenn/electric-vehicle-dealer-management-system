package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
public class DealerHierarchyService {

//    private final DealerHierarchyRepository dealerHierarchyRepository;
//    private final ManufacturerService manufacturerService;
//
//    public void rankDealer(Long dealerId, int newLevel) {
//
//        User dealer = manufacturerService.getDealerByDealerId(dealerId);
//
//        if (newLevel < 1 || newLevel > 3)
//            throw new IllegalArgumentException("Invalid dealer level: " + newLevel);
//
//        if (!dealer.getRole().equals(RoleType.DEALER_MANAGER))
//            throw new IllegalStateException("Only Dealer Manager can change rank.");
//
//        dealer.setLevel(newLevel);
//        manufacturerService.updatePartialUser(dealer.getId(), )
//    }

}
