package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerInfoUpdateDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerListDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.repository.CustomerRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealerService extends UserService {

    private final CustomerRepository customerRepository;

    public DealerService(PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         CustomerRepository customerRepository) {
        super(passwordEncoder, userRepository);
        this.customerRepository = customerRepository;
    }

    public CustomerListDto getAllCustomersByCurrentDealer(int pageNo, int pageSize) {

        User currentDealer = getCurrentUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Customer> customerPage = customerRepository.findByDealer(currentDealer, pageable);

        List<CustomerDetailGetDto> customerDetailGetDtos = customerPage.getContent()
                .stream()
                .map(CustomerDetailGetDto::fromModel)
                .toList();

        return new CustomerListDto(
                customerDetailGetDtos,
                customerPage.getNumber(),
                customerPage.getSize(),
                (int) customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isLast()
        );
    }

    public CustomerDetailGetDto getCustomerByPhone(String phoneNumber) {
        Customer customer = customerRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CUSTOMER_WITH_PHONE_NUMBER_NOT_EXIST, phoneNumber));
        return CustomerDetailGetDto.fromModel(customer);
    }

    public CustomerDetailGetDto createCustomerIfNotExists(CustomerPostDto customerPostDto) {

        User currentDealer = getCurrentUser();
        Optional<Customer> existingCustomer =
                customerRepository.findByPhoneAndDealer(customerPostDto.phone(), currentDealer);

        if (existingCustomer.isPresent()) {
            return CustomerDetailGetDto.fromModel(existingCustomer.get());
        }

        validateCustomerEmail(customerPostDto.email());
        validateCustomerPhone(customerPostDto.phone());
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

    public void updateCustomerInfoByCustomerIdOrPhoneNumber(
            Long customerId,
            String phoneNumber,
            CustomerInfoUpdateDto customerInfoUpdateDto) {

        if (customerId == null && phoneNumber == null) {
            throw new IllegalArgumentException("Either customerId or phoneNumber must be provided");
        }

        Customer customer = null;
        if (phoneNumber != null)
            customer = customerRepository.findByPhone(phoneNumber)
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CUSTOMER_WITH_PHONE_NUMBER_NOT_EXIST, phoneNumber));

        if (customerId != null)
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CUSTOMER_NOT_FOUND, customerId));

        if (customerInfoUpdateDto.email() != null && !customerInfoUpdateDto.email().isBlank()) {
            validateCustomerEmail(customerInfoUpdateDto.email());
            customer.setEmail(customerInfoUpdateDto.email());
        }

        if (customerInfoUpdateDto.phone() != null && !customerInfoUpdateDto.phone().isBlank()) {
            validateCustomerPhone(customerInfoUpdateDto.phone());
            customer.setPhone(customerInfoUpdateDto.phone());
        }

        if (customerInfoUpdateDto.fullName() != null && !customerInfoUpdateDto.fullName().isBlank()) {
            customer.setFullName(customerInfoUpdateDto.fullName());
        }

        customerRepository.save(customer);
    }

    private void validateCustomerEmail(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicatedException(Constants.ErrorCode.EMAIL_ALREADY_EXIST, email);
        }
    }

    private void validateCustomerPhone(String phone) {
        if (customerRepository.existsByPhone(phone)) {
            throw new DuplicatedException(Constants.ErrorCode.PHONE_ALREADY_EXIST, phone);
        }
    }

}
