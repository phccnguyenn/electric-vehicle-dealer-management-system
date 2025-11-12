package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.exception.PriceProgramViolationException;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.*;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.ProgramDetailGetDto;
import com.evdealer.ev_dealer_management.sale.service.PriceProgramService;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import com.itextpdf.text.DocumentException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FileGenerator fileGenerator;
    private final DealerService dealerService;
    private final CarDetailRepository carDetailRepository;
    private final OrderActivityService orderActivityService;
    private final PriceProgramService priceProgramService;

    private boolean constraintCarDetailPriceByPriceProgram(Integer currentLevel, String carModelName, BigDecimal totalAmount) {
        OffsetDateTime currentDate = OffsetDateTime.now();

        List<PriceProgramGetDto> priceProgramGetDto = priceProgramService.getByDealerHierarchy(currentLevel);
        List<PriceProgramGetDto> activePrograms = priceProgramGetDto.stream()
                .filter(p -> currentLevel.equals(p.dealerHierarchy()) &&
                        !currentDate.isBefore(p.startDate()) &&
                        !currentDate.isAfter(p.endDate()))
                .toList();

        // If there is not any price program for this car model -> accept current price
        if (activePrograms.isEmpty())
            return true;

        for (PriceProgramGetDto program : activePrograms) {
            // List programDetail only contain this carModel
            List<ProgramDetailGetDto> detailsForCar = program.programDetails().stream()
                    .filter(d -> d.carModelName().equals(carModelName))
                    .toList();

            if (detailsForCar.isEmpty()) {
                // CarModel is not exist → accept this price
                continue;
            }

            // check price for this car detail
            boolean valid = detailsForCar.stream()
                    .anyMatch(d -> totalAmount.compareTo(d.minPrice()) >= 0 &&
                            totalAmount.compareTo(d.maxPrice()) <= 0);

            if (valid) {
                return true; // There is at least one PriceProgram
            } else {
                return false; // if CarModel existed in PriceProgram but the Price for it is invalid
            }
        }

        return true;
    }

    public OrderDetailDto createOrder(OrderCreateDto dto) {
        CarDetail carDetail = carDetailRepository.findById(dto.carId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        User staff = dealerService.getCurrentUser();
        Customer customer = null;
        try {
            customer = dealerService.getCustomerByPhone(dto.customerPhone(), Customer.class);
        } catch (NotFoundException e) {
            CustomerPostDto customerPostDto = new CustomerPostDto(dto.customerName(), null, dto.customerPhone(), null);
            customer = dealerService.createCustomerIfNotExists(customerPostDto, Customer.class);
        }

        // Constraint By Price Program
        Integer currentLevel = staff.getParent().getDealerHierarchy().getLevelType();
        String carModelName = carDetail.getCarModel().getCarModelName();
        if (!constraintCarDetailPriceByPriceProgram(currentLevel, carModelName, dto.totalAmount())) {
            throw new PriceProgramViolationException("The total amount for this car is not accepted");
        }

        Order order = Order.builder()
                .carDetail(carDetail)
                .staff(staff)
                .customer(customer)
                .totalAmount(dto.totalAmount())
                .amountPaid(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        order = orderRepository.save(order);
        orderActivityService.logActivity(order.getId(), OrderStatus.PENDING);
        // Save entity, not DTO
        try {
            fileGenerator.generateQuotation(order);
            fileGenerator.generateContract(order);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Failed to generate files for the order", e);
        }

        return OrderDetailDto.fromModel(order);
    }

    public OrderDetailDto getOrderDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderDetailDto.fromModel(order);
    }

    public List<OrderFileDto> getOrderFilesByCustomerPhone(String phone) {
        return orderRepository.findFileUrlsByCustomerPhone(phone);
    }
    public OrderDetailDto updateOrder(Long id, OrderUpdateDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (dto.status() != null && dto.status() != order.getStatus()) {
            orderActivityService.logActivity(order.getId(), dto.status());
        }

        if (dto.totalAmount() != null) order.setTotalAmount(dto.totalAmount());
        if (dto.quotationUrl() != null) order.setQuotationUrl(dto.quotationUrl());
        if (dto.contractUrl() != null) order.setContractUrl(dto.contractUrl());

        return OrderDetailDto.fromModel(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        orderActivityService.logActivity(order.getId(), OrderStatus.CANCELLED);
    }

    public List<Order> getOrders(Optional<Long> staffId, Optional<OrderStatus> status) {
        List<Order> orders;

        if (staffId.isPresent()) {
            orders = orderRepository.findByStaff_Id(staffId.get());
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream()
                .filter(o -> status.map(s -> o.getStatus() == s)
                        .orElse(o.getStatus() != OrderStatus.CANCELLED))
                .collect(Collectors.toList());
    }
}
