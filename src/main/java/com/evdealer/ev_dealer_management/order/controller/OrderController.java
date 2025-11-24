//package com.evdealer.ev_dealer_management.order.controller;
//
//import com.evdealer.ev_dealer_management.order.model.OrderActivity;
//import com.evdealer.ev_dealer_management.order.model.dto.*;
//import com.evdealer.ev_dealer_management.order.model.dto.evm.OrderListDto;
//import com.evdealer.ev_dealer_management.order.model.dto.evm.OrderUpdateForEVMDto;
//import com.evdealer.ev_dealer_management.order.model.enumeration.OrderStatus;
//import com.evdealer.ev_dealer_management.order.service.OrderActivityService;
//import com.evdealer.ev_dealer_management.order.service.OrderService;
//import com.evdealer.ev_dealer_management.order.service.PaymentService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/v1/orders")
//@RequiredArgsConstructor
//@Tag(name = "Orders", description = "Order management APIs")
//public class OrderController {
//
//    private final OrderService orderService;
//    private final OrderActivityService orderActivityService;
//    private final PaymentService paymentService;
//
//    @GetMapping("/pending")
//    public ResponseEntity<OrderListDto> getPendingOrders (
//            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return ResponseEntity.ok(orderService.getAllOrderWithPendingStatus(pageNo, pageSize));
//    }
//
//    @GetMapping("/status")
//    public ResponseEntity<OrderListDto> getOrdersByStatus (
//            @RequestParam(name = "orderStatus", required = true) OrderStatus orderStatus,
//            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return ResponseEntity.ok(orderService.getAllOrderByStatus(orderStatus, pageNo, pageSize));
//    }
//
//    @PatchMapping("/approve-order")
//    public ResponseEntity<OrderDetailDto> OrderApprovalRequestByEVM(
//            OrderUpdateForEVMDto orderUpdateForEVMDto) {
//        return ResponseEntity.ok(orderService.orderApprovalRequestByEVM(orderUpdateForEVMDto));
//    }
//
//    @Operation(summary = "Get order by ID") // Nhân viên
//    @GetMapping("/{id}")
//    public OrderDetailDto getOrder(@PathVariable Long id) {
//        return orderService.getOrderDetail(id);
//    }
//
//    @Operation(summary = "List orders") // Nhân viên
//    @GetMapping
//    public List<OrderDetailDto> listOrders(
//            @RequestParam Optional<Long> staffId,
//            @RequestParam Optional<OrderStatus> status
//    ) {
//        return orderService.getOrders(staffId, status)
//                .stream()
//                .map(OrderDetailDto::fromModel)
//                .collect(Collectors.toList());
//    }
//
////    @Operation(summary = "Create a new order") // Nhân viên
////    @PostMapping
////    public OrderDetailDto createOrder(@RequestBody OrderCreateDto dto) {
////        return orderService.createOrder(dto);
////    }
//
//    @Operation(summary = "Update order details or change status") // Dealer Manager / Staff
//    @PatchMapping("/{id}")
//    public ResponseEntity<OrderDetailDto> updateOrder(
//            @PathVariable Long id,
//            @RequestBody OrderUpdateDto dto
//    ) {
//        OrderDetailDto updatedOrder = orderService.updateOrder(id, dto);
//        return ResponseEntity.ok(updatedOrder);
//    }
//
////    @Operation(summary = "Get orders by dealer")
////    @GetMapping("/dealer")
////    public List<OrderDetailDto> getOrdersByDealer() {
////        return orderService.getOrdersByDealer();
////    }
////
////    @GetMapping("/files") // Nhân viên
////    public ResponseEntity<List<OrderFileDto>> getFilesByCustomerPhone(
////            @RequestParam String phone
////    ) {
////        return ResponseEntity.ok(orderService.getOrderFilesByCustomerPhone(phone));
////    }
//
//    @Operation(summary = "Add payment to an order") // Nhân viên
//    @PostMapping("/{id}/payments")
//    public OrderDetailDto addPayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
//        return paymentService.addPayment(id, paymentDto);
//    }
//
//    @Operation(summary = "Get payments of an order") // Nhân viên
//    @GetMapping("/{id}/payments")
//    public List<PaymentResponseDto> getPayments(@PathVariable Long id) {
//        return paymentService.getPayments(id);
//    }
//
//    @Operation(summary = "Soft-delete order by setting status to CANCELLED") // Nhân viên
//    @DeleteMapping("/{id}")
//    public void deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrder(id);
//    }
//
//    @Operation(summary = "Get revenue by staff") // Chỉ có dealer manager được coi
//    @GetMapping("/reports/revenue/staff")
//    public List<RevenueByStaffDto> getRevenueByStaff(@RequestParam(required = false) Long staffId) {
//        return paymentService.getRevenueByStaff(staffId);
//    }
//
//    // ADMIN - DONE
//    @Operation(summary = "Get revenue by dealers") // Bên hãng xe - admin
//    @GetMapping("/reports/revenue/dealer")
//    public List<RevenueByDealerDto> getRevenueByDealer() {
//        return paymentService.getRevenueByDealer();
//    }
//
//    @Operation(summary = "Get revenue by city") // Bên hãng xe - admin
//    @GetMapping("/reports/revenue/city")
//    public List<RevenueByCityDto> getRevenueByCity() {
//        return paymentService.getRevenueByCity();
//    }
//
////    @Operation(summary = "Get debts of customers") // Bên hãng xe - admin và nhân viên EVM
////    @GetMapping("/reports/customer-debts")
////    public List<CustomerDebtDto> getCustomerDebts() {
////
////        return paymentService.getCustomerDebts();
////    }
//
//    @GetMapping("/{orderId}/activities")
//    public ResponseEntity<OrderActivitiesResponse> getOrderActivities(@PathVariable Long orderId) {
//        List<OrderActivity> activities = orderActivityService.getActivities(orderId);
//
//        List<OrderActivityDto> dto = activities.stream()
//                .map(a -> new OrderActivityDto(a.getId(), a.getOrderStatus(), a.getChangedAt()))
//                .toList();
//
//        OrderActivitiesResponse response = new OrderActivitiesResponse(orderId, dto);
//
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/sales-speed")
//    public ResponseEntity<List<SalesSpeedResponseDto>> getSalesSpeedByDealer(
//            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
//            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
//    ) {
//        return ResponseEntity.ok(
//                orderActivityService.getSalesSpeedByDealer(startTime, endTime)
//        );
//    }
//}
