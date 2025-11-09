package com.evdealer.ev_dealer_management.testdrive.service;

import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.testdrive.model.Booking;
import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.testdrive.model.dto.BookingGetDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.BookingPostDto;
import com.evdealer.ev_dealer_management.testdrive.repository.BookingRepository;
import com.evdealer.ev_dealer_management.testdrive.repository.SlotRepository;
import com.evdealer.ev_dealer_management.user.model.Customer;
import com.evdealer.ev_dealer_management.user.model.dto.customer.CustomerPostDto;
import com.evdealer.ev_dealer_management.user.repository.CustomerRepository;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final DealerService dealerService;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;

    @Transactional
    public BookingGetDto createBooking(BookingPostDto bookingPostDto) {

        CustomerPostDto customerPostDto = new CustomerPostDto(bookingPostDto.customerName(), "", bookingPostDto.customerPhone(), "");

        Customer customer = customerRepository.findByPhone(bookingPostDto.customerPhone())
                .orElse(dealerService.createCustomerIfNotExists(customerPostDto, Customer.class));

        Slot slot = slotRepository.findById(bookingPostDto.slotId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.SLOT_NOT_FOUND, bookingPostDto.slotId()));

        // check capacity
        int bookedCount = bookingRepository.countBySlot(slot);
        if (bookedCount >= slot.getNumCustomers()) {
            throw new IllegalStateException("Slot is fully booked");
        }

        Booking booking = Booking.builder()
                .slot(slot)
                .customerName(customer.getFullName())
                .customerPhone(customer.getPhone())
                .build();

        return BookingGetDto.fromModel(bookingRepository.save(booking));
    }

    public List<BookingGetDto> getBookingsBySlot(Long slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.SLOT_NOT_FOUND, slotId));
        return bookingRepository.findAllBySlot(slot)
                .stream()
                .map(BookingGetDto::fromModel)
                .toList();
    }

    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.BOOKING_NOT_FOUND, bookingId));
        bookingRepository.delete(booking);
    }

}
