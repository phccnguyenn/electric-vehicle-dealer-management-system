package com.evdealer.ev_dealer_management.order.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.repository.CarDetailRepository;
import com.evdealer.ev_dealer_management.car.repository.CarModelRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.order.model.Order;
import com.evdealer.ev_dealer_management.order.model.dto.*;
import com.evdealer.ev_dealer_management.order.model.dto.evm.OrderListDto;
import com.evdealer.ev_dealer_management.order.model.dto.evm.OrderUpdateForEVMDto;
import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentStatus;
import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentType;
import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
import com.evdealer.ev_dealer_management.sale.model.dto.PriceProgramGetDto;
import com.evdealer.ev_dealer_management.sale.model.dto.ProgramDetailGetDto;
import com.evdealer.ev_dealer_management.sale.service.PriceProgramService;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import com.evdealer.ev_dealer_management.warehouse.model.Warehouse;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseCar;
import com.evdealer.ev_dealer_management.warehouse.model.WarehouseTransfer;
import com.evdealer.ev_dealer_management.warehouse.model.dto.WarehouseCarUpdateDto;
import com.evdealer.ev_dealer_management.warehouse.model.enumeration.WarehouseCarStatus;
import com.evdealer.ev_dealer_management.warehouse.service.WarehouseService;
import com.evdealer.ev_dealer_management.warehouse.service.WarehouseTransferService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WarehouseService warehouseService;
    private final OrderRepository orderRepository;
    private final FileGenerator fileGenerator;
    private final DealerService dealerService;
    private final CarModelRepository carModelRepository;
    private final CarDetailRepository carDetailRepository;
    private final OrderActivityService orderActivityService;
    private final PriceProgramService priceProgramService;
    private final WarehouseTransferService warehouseTransferService;

    private boolean constraintCarDetailPriceByPriceProgram(String carModelName,
                                                           boolean isSpecialColor,
                                                           BigDecimal totalAmount) {

        OffsetDateTime currentDate = OffsetDateTime.now();

        List<PriceProgramGetDto> pricePrograms = priceProgramService.getCurrentAndUpcomingPriceProgram();

        // Chỉ lấy chương trình đã bắt đầu (đang effective)
        Optional<PriceProgramGetDto> ongoingProgramOpt = pricePrograms.stream()
                .filter(p -> (p.effectiveDate().isBefore(currentDate) ||
                        p.effectiveDate().isEqual(currentDate))
                        && p.isActive())
                .findFirst();

        if (ongoingProgramOpt.isEmpty()) return true;

        PriceProgramGetDto ongoing = ongoingProgramOpt.get();

        // Lọc chi tiết đúng model + màu
        List<ProgramDetailGetDto> detailsForCar = ongoing.programDetails().stream()
                .filter(d -> d.carModelName().equals(carModelName)
                        && d.isSpecialColor() == isSpecialColor)
                .toList();

        if (detailsForCar.isEmpty()) return true;

        return detailsForCar.stream()
                .anyMatch(d -> totalAmount.compareTo(d.minPrice()) >= 0 &&
                        totalAmount.compareTo(d.maxPrice()) <= 0);
    }

    @Transactional
    public OrderDetailDto createOrderByStatus(OrderCreateDto orderCreateDto) {
        OrderStatusCreate statusCreate = orderCreateDto.orderStatus();
        OrderStatus status = OrderStatus.valueOf(statusCreate.name());

        OrderDetailDto orderDetailDto;

        if (status == OrderStatus.PENDING) {
            orderDetailDto = createOrderPendingWithManufacturer(orderCreateDto);
        } else if (status == OrderStatus.DEMO_AVAILABLE) {
            orderDetailDto = createOrderDemoAvailable(orderCreateDto);
        } else {
            throw new IllegalArgumentException("Unsupported order status: " + status);
        }

        return orderDetailDto;
    }

    @Transactional
    public OrderDetailDto createOrderDemoAvailable(OrderCreateDto orderCreateDto) {

        Customer customer = null;
        try {
            customer = dealerService.getCustomerByPhone(orderCreateDto.customerPhone(), Customer.class);
        } catch (NotFoundException e) {
            CustomerPostDto customerPostDto = new CustomerPostDto(orderCreateDto.customerName(), null, orderCreateDto.customerPhone(), null);
            customer = dealerService.createCustomerIfNotExists(customerPostDto, Customer.class);
        }

        CarModel carModel = carModelRepository.findById(orderCreateDto.carModelId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, orderCreateDto.carModelId()));

        CarDetail carDetail = carDetailRepository.findById(orderCreateDto.carDetailId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, orderCreateDto.carDetailId()));

        User user = dealerService.getCurrentUser();
        Order order = Order.builder()
                .dealerInfo(user.getDealerInfo())
                .carDetail(carDetail)
                .carModel(carModel)
                .staff(user)
                .customer(customer)
                .totalAmount(orderCreateDto.totalAmount())
                .amountPaid(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        order = orderRepository.save(order);
        orderActivityService.logActivity(order.getId(), OrderStatus.PENDING);

        return OrderDetailDto.fromModel(order);
    }

    @Transactional
    public OrderDetailDto createOrderPendingWithManufacturer(OrderCreateDto dto) {

        Customer customer = null;
        try {
            customer = dealerService.getCustomerByPhone(dto.customerPhone(), Customer.class);
        } catch (NotFoundException e) {
            CustomerPostDto customerPostDto = new CustomerPostDto(dto.customerName(), null, dto.customerPhone(), null);
            customer = dealerService.createCustomerIfNotExists(customerPostDto, Customer.class);
        }

        // Constraint By Price Program
        CarModel carModel = carModelRepository.findById(dto.carModelId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_MODEL_NOT_FOUND, dto.carModelId()));

//        if (!constraintCarDetailPriceByPriceProgram(carModel.getCarModelName(), dto.isSpecialColor(), dto.totalAmount()))
//            throw new PriceProgramViolationException("The total amount for this carDetail is not accepted");

        // Create order
        User user = dealerService.getCurrentUser();
        Order order = Order.builder()
                .dealerInfo(user.getDealerInfo())
                .carDetail(null)
                .carModel(carModel)
                .staff(user)
                .customer(customer)
                .totalAmount(dto.totalAmount())
                .amountPaid(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        order = orderRepository.save(order);
        orderActivityService.logActivity(order.getId(), OrderStatus.PENDING);

        return OrderDetailDto.fromModel(order);
    }

    public OrderListDto getAllOrderByStatus(OrderStatus orderStatus, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findAllByStatus(orderStatus, pageable);

        List<OrderDetailDto> orderDetailDtos = orderPage.getContent()
                .stream()
                .map(OrderDetailDto::fromModel)
                .toList();

        return new OrderListDto (
                orderDetailDtos,
                orderPage.getNumber(),
                orderPage.getSize(),
                (int) orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast()
        );
    }

    public OrderDetailDto orderApprovalRequestByEVM(OrderUpdateForEVMDto dto) {
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.ORDER_NOT_FOUND, dto.orderId()));

        CarDetail carDetail = carDetailRepository.findById(dto.carDetailId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CAR_DETAIL_NOT_FOUND, dto.carDetailId()));

        BigDecimal totalPaid = order.getAmountPaid();
        BigDecimal totalAmount = order.getTotalAmount();
        BigDecimal depositRequired = totalAmount.multiply(new BigDecimal("0.3"));

        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = dto.orderStatus();

        if (newStatus != null && newStatus != oldStatus) {
            if (oldStatus == OrderStatus.PENDING && newStatus == OrderStatus.APPROVED) {
                if (totalPaid.compareTo(depositRequired) < 0) {
                    throw new IllegalArgumentException(
                            "Không thể duyệt đơn khi chưa đặt cọc đủ 30% tổng giá trị đơn."
                    );
                }
            }

            if (oldStatus == OrderStatus.DELIVERED && newStatus == OrderStatus.COMPLETED) {
                if (totalPaid.compareTo(totalAmount) < 0) {
                    throw new IllegalArgumentException(
                            "Không thể hoàn thành đơn khi chưa thanh toán đủ 100%."
                    );
                }
            }

            order.setStatus(newStatus);
            orderActivityService.logActivity(order.getId(), newStatus);
        }

        order.setCarDetail(carDetail);

        Order savedOrder = orderRepository.save(order);

        generateQuotationAndContractFile(savedOrder);

        // Due to random for creating order, decrease one unit in warehouse
        // Log for car import & export for warehouse
        WarehouseCarUpdateDto warehouseCarUpdateDto = new WarehouseCarUpdateDto(
                carDetail.getCarModel().getId(),
                carDetail.getCarModel().getCarDetails().isEmpty() ? 0 : carDetail.getCarModel().getCarDetails().size() - 1,
                carDetail.getCarModel().getCarDetails().isEmpty() ? WarehouseCarStatus.OUT_OF_STOCK : WarehouseCarStatus.IN_STOCK
        );
        WarehouseCar warehouseCar = warehouseService.updateWarehouseCar(carDetail.getCarModel().getId(), warehouseCarUpdateDto);
        Warehouse warehouse = warehouseCar.getWarehouse();
        warehouseTransferService.logTransfer(
                warehouse,
                carDetail.getId(),
                "Kho Tổng Khu Vực TP. Hồ Chí Minh",
                order.getDealerInfo().getLocation(),
                null
        );
        // ---

        return OrderDetailDto.fromModel(savedOrder);
    }

    private void generateQuotationAndContractFile(Order order) {
        try {
            fileGenerator.generateQuotation(order);
            fileGenerator.generateContract(order);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Failed to generate files for the order", e);
        }
    }
//
    public OrderDetailDto getOrderDetail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderDetailDto.fromModel(order);
    }
//
//    public List<OrderFileDto> getOrderFilesByCustomerPhone(String phone) {
//        return orderRepository.findFileUrlsByCustomerPhone(phone);
//    }
//
    @Transactional
    public OrderDetailDto updateOrder(Long id, OrderUpdateDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        BigDecimal totalPaid = order.getAmountPaid();
        BigDecimal totalAmount = order.getTotalAmount();
        BigDecimal depositRequired = totalAmount.multiply(new BigDecimal("0.3"));

        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = dto.status();

        if(newStatus != null && newStatus != oldStatus) {
            if (oldStatus == OrderStatus.PENDING &&
                    newStatus == OrderStatus.APPROVED) {

                if (totalPaid.compareTo(depositRequired) < 0) {
                    throw new IllegalArgumentException(
                            "Không thể duyệt đơn khi chưa đặt cọc đủ 30% tổng giá trị đơn."
                    );
                }
            }

            if (oldStatus == OrderStatus.DELIVERED &&
                    newStatus == OrderStatus.COMPLETED) {

                if (order.getPayments().stream().anyMatch(p -> p.getType() == PaymentType.IN_FULL)
                && totalPaid.compareTo(totalAmount) < 0) {
                    throw new IllegalArgumentException(
                            "Không thể hoàn thành đơn khi chưa thanh toán đủ 100%."
                    );
                }
            }

            order.setStatus(newStatus);
            orderActivityService.logActivity(order.getId(), newStatus);
        }
        if (dto.totalAmount() != null) order.setTotalAmount(dto.totalAmount());
        if (dto.quotationUrl() != null) order.setQuotationUrl(dto.quotationUrl());
        if (dto.contractUrl() != null) order.setContractUrl(dto.contractUrl());

        return OrderDetailDto.fromModel(orderRepository.save(order));
    }

//
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

    public List<OrderDetailDto> getOrdersByDealer(Long dealerId) {

        List<Order> orders;

        // Hãng xe thì FE chỉ gần dealerId = null để lấy tất cả đơn hàng là được
        if (dealerId == null) {
            orders = orderRepository.findAll();
        } else { // này thì đại lý nào chỉ được coi TẤT CẢ các đơn thuộc về đại lý đó th
            orders = orderRepository.findByDealerInfo_Id(dealerId);
        }

        return orders.stream()
                .map(OrderDetailDto::fromModel)
                .toList();
    }
}
