//package com.evdealer.ev_dealer_management.order.service;
//
//import com.evdealer.ev_dealer_management.order.model.Order;
//import com.evdealer.ev_dealer_management.order.model.Payment;
//import com.evdealer.ev_dealer_management.order.model.dto.*;
//import com.evdealer.ev_dealer_management.order.model.enumeration.PaymentType;
//import com.evdealer.ev_dealer_management.order.repository.OrderRepository;
//import com.evdealer.ev_dealer_management.order.repository.PaymentRepository;
//import com.evdealer.ev_dealer_management.user.model.User;
//import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
//import com.evdealer.ev_dealer_management.user.service.DealerService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final OrderRepository orderRepository;
//    private final DealerService dealerService;
//
//    // Add a new payment to an order
//    @Transactional
//    public OrderDetailDto addPayment(Long orderId, PaymentDto dto) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//        // Tổng tiền của chiếc xe
//        BigDecimal totalAmount = order.getTotalAmount();
//        BigDecimal paidAmount = order.getAmountPaid();
//        // tổng số tiền đã thanh toán
//        BigDecimal extraAmount = dto.amount();
//        BigDecimal newTotalPaid = paidAmount.add(extraAmount);
//
//        if (dto.type() == PaymentType.IN_FULL && extraAmount.compareTo(totalAmount) < 0) {
//            throw new IllegalArgumentException(
//                    "Thanh toán IN_FULL yêu cầu phải trả đủ toàn bộ số tiền của đơn hàng trong LẦN ĐẦU TIÊN."
//            );
//        }
//
//        if (dto.amount().compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Số tiền thanh toán phải lớn hơn 0.");
//        }
//
//        if (newTotalPaid.compareTo(totalAmount) > 0) {
//            throw new IllegalArgumentException("Số tiền thanh toán vượt quá tổng giá trị đơn hàng.");
//        }
//        Payment payment = Payment.builder()
//                .order(order)
//                .amount(dto.amount())
//                .type(dto.type())
//                .paidAt(LocalDateTime.now())
//                .build();
//
//        order.addPayment(payment);
//        paymentRepository.save(payment);
//        orderRepository.save(order);
//
//        return OrderDetailDto.fromModel(order);
//    }
//
//    // Get payments for an order
//    public List<PaymentResponseDto> getPayments(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        return order.getPayments().stream()
//                .map(PaymentResponseDto::fromModel)
//                .collect(Collectors.toList());
//    }
//
//    // Revenue / reporting
//    public List<RevenueByStaffDto> getRevenueByStaff(Long staffId) {
//        Long dealerId = dealerService.getCurrentUser().getId();
//        return paymentRepository.getRevenueByStaff(staffId, dealerId);
//    }
//
//    public List<RevenueByDealerDto> getRevenueByDealer() {
//        return paymentRepository.getRevenueByDealer();
//    }
//
//    public List<RevenueByCityDto> getRevenueByCity() {
//        return paymentRepository.getRevenueByCity();
//    }
//
////    public List<CustomerDebtDto> getCustomerDebts() {
////        User currentUser = dealerService.getCurrentUser();
////        Long dealerId;
////
////        if (currentUser.getRole() == RoleType.DEALER_MANAGER) {
////            dealerId = currentUser.getId();
////        } else {
////            dealerId = currentUser.getParent() != null ? currentUser.getParent().getId() : null;
////        }
////
////        if (dealerId == null) {
////            return Collections.emptyList();
////        }
////
////        return paymentRepository.getCustomerDebts(dealerId);
////    }
//}
