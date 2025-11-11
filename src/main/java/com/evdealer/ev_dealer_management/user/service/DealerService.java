package com.evdealer.ev_dealer_management.user.service;

import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.DealerHierarchy;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserInfoListDto;
import com.evdealer.ev_dealer_management.user.model.dto.account.UserProfileGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerDetailGetDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerInfoUpdateDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerListDto;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.CustomerRepository;
import com.evdealer.ev_dealer_management.user.repository.DealerHierarchyRepository;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DealerService extends UserService {

    private final CustomerRepository customerRepository;

    public DealerService(PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         CustomerRepository customerRepository,
                         DealerHierarchyRepository dealerHierarchyRepository) {
        super(passwordEncoder, userRepository, dealerHierarchyRepository);
        this.customerRepository = customerRepository;
    }

    public UserInfoListDto getAllStaffByCurrentManager(int pageNo, int pageSize) {

        User currentManager = getCurrentUser();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> staffPage = userRepository.findAllByParentId(currentManager.getId(), pageable);

        List<UserProfileGetDto> staffProfileGetDtos = staffPage.getContent()
                .stream()
                .map(UserProfileGetDto::fromModel)
                .toList();

        return new UserInfoListDto(
                staffProfileGetDtos,
                staffPage.getNumber(),
                staffPage.getSize(),
                (int) staffPage.getTotalElements(),
                staffPage.getTotalPages(),
                staffPage.isLast()
        );
    }

    //
    @Transactional
    public User getDealerStaffByDealerManager(Long managerId, Long dealerStaffId) {
        User dealerManager = userRepository.findByIdWithChildren(managerId)
                .orElseThrow(() -> new RuntimeException("Dealer Manager not found"));

        return dealerManager.getChildren().stream()
                .filter(child -> Objects.equals(child.getId(), dealerStaffId)
                        && child.getRole().equals(RoleType.DEALER_STAFF))
                .findFirst()
                .orElse(null);
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

    public <T> T getCustomerByPhone(String phoneNumber, Class<T> type) {
        Customer customer = customerRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CUSTOMER_WITH_PHONE_NUMBER_NOT_EXIST, phoneNumber));

        if (type.equals(Customer.class)) {
            return type.cast(customer); // return entity
        } else if (type.equals(CustomerDetailGetDto.class)) {
            return type.cast(CustomerDetailGetDto.fromModel(customer)); // return DTO
        } else {
            throw new IllegalArgumentException("Unsupported return type: " + type);
        }

    }

    public <T> T createCustomerIfNotExists(CustomerPostDto customerPostDto, Class<T> type) {

        User currentDealer = getCurrentUser();
        Optional<Customer> existingCustomer =
                customerRepository.findByPhoneAndDealer(customerPostDto.phone(), currentDealer);

        if (existingCustomer.isPresent()) {
            if (type.equals(Customer.class)) {
                return type.cast(existingCustomer.get()); // return entity
            } else if (type.equals(CustomerDetailGetDto.class)) {
                return type.cast(CustomerDetailGetDto.fromModel(existingCustomer.get())); // return DTO
            } else {
                throw new IllegalArgumentException("Unsupported return type: " + type);
            }
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

        if (type.equals(Customer.class)) {
            return type.cast(savedCustomer); // return entity
        } else if (type.equals(CustomerDetailGetDto.class)) {
            return type.cast(CustomerDetailGetDto.fromModel(savedCustomer)); // return DTO
        } else {
            throw new IllegalArgumentException("Unsupported return type: " + type);
        }
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
