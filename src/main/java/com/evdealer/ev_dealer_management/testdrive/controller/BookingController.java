package com.evdealer.ev_dealer_management.testdrive.controller;

import com.evdealer.ev_dealer_management.testdrive.model.dto.BookingGetDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.BookingPostDto;
import com.evdealer.ev_dealer_management.testdrive.service.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@Tag(name = "Test Drive", description = "Test Drive APIs for Dealer only")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/slot/{slotId}")
    public ResponseEntity<List<BookingGetDto>> getBookingsBySlot(@PathVariable Long slotId) {
        List<BookingGetDto> bookings = bookingService.getBookingsBySlot(slotId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/create")
    public ResponseEntity<BookingGetDto> createBooking(@RequestBody BookingPostDto bookingPostDto) {
        BookingGetDto booking = bookingService.createBooking(bookingPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

}
