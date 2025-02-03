package com.ABCFitness.Gym.controller;

import com.ABCFitness.Gym.dto.BookingDTO;
import com.ABCFitness.Gym.service.BookingService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookclass")
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookingDTO>> searchBookings(
        @RequestParam(required = false) String memberName,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(bookingService.searchBookings(memberName, startDate, endDate));
    }
}
