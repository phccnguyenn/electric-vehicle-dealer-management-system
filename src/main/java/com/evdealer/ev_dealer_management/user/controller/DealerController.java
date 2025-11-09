package com.evdealer.ev_dealer_management.user.controller;

import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerInfoUpdateDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerListDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dealer")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users management APIs")
public class DealerController {

    private final DealerService dealerService;

    @GetMapping("/customers")
    public ResponseEntity<CustomerListDto> getAllCustomers(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(dealerService.getAllCustomersByCurrentDealer(pageNo, pageSize));
    }

    @GetMapping("/customers/by-phone")
    public ResponseEntity<CustomerDetailGetDto> getCustomerInfoByPhone(@RequestParam(name = "phone") String phone) {
        return ResponseEntity.ok(dealerService.getCustomerByPhone(phone, CustomerDetailGetDto.class));
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDetailGetDto> createNewCustomer(@RequestBody CustomerPostDto customerPostDto) {
        CustomerDetailGetDto customerDetail = dealerService.createCustomerIfNotExists(customerPostDto, CustomerDetailGetDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDetail);
    }

    @PatchMapping("/customers/{customerId}/update-info")
    public ResponseEntity<Void> updateCustomerInfoByCustomerId(
            @PathVariable(name = "customerId") Long customerId,
            @RequestBody CustomerInfoUpdateDto customerInfoUpdateDto
    ) {
        dealerService.updateCustomerInfoByCustomerIdOrPhoneNumber(customerId, null, customerInfoUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/customers/update-info/by-phone")
    public ResponseEntity<Void> updateCustomerInfoByCustomerPhone(
            @RequestParam(name = "phone") String phoneNumber,
            @RequestBody CustomerInfoUpdateDto customerInfoUpdateDto
    ) {
        dealerService.updateCustomerInfoByCustomerIdOrPhoneNumber(null, phoneNumber, customerInfoUpdateDto);
        return ResponseEntity.noContent().build();
    }

}
