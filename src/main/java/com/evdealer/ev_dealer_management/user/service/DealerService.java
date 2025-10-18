package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.InvalidAuthenticationPrincipalException;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.dto.CustomerDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.repository.CustomerRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public CustomerDetailGetDto createCustomerIfNotExists(CustomerPostDto customerPostDto) {

        User currentDealer = getCurrentUser();
        Optional<Customer> existingCustomer =
                customerRepository.findByPhoneAndDealer(customerPostDto.phone(), currentDealer);

        if (existingCustomer.isPresent()) {
            return CustomerDetailGetDto.fromModel(existingCustomer.get());
        }

        Customer newCustomer = Customer.builder()
                .dealer(currentDealer)
                .fullName(customerPostDto.fullName())
                .email(customerPostDto.email())
                .phone(customerPostDto.phone())
                .address(customerPostDto.address())
                .build();
        Customer savedCustomer = customerRepository.save(newCustomer);

        currentDealer.getCustomers().add(savedCustomer);
        userRepository.save(currentDealer);

        return CustomerDetailGetDto.fromModel(savedCustomer);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = null;
        if (principal instanceof User user)
            currentUser = user;
        else
            throw new InvalidAuthenticationPrincipalException(Constants.ErrorCode.PRINCIPAL_IS_NOT_USER, principal);

        return currentUser;
    }
}
