package com.evdealer.ev_dealer_management.user.controller;

import com.evdealer.ev_dealer_management.user.model.dto.CustomerDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dealer")
@RequiredArgsConstructor
public class DealerController {

//    private final DealerService dealerService;
//
//    @PostMapping("/customer")
//    public ResponseEntity<CustomerDetailGetDto> createNewCustomer(@RequestBody CustomerPostDto dto) {
//        return ResponseEntity.ok(dealerService.createCustomerIfNotExists(dto));
//    }

}
