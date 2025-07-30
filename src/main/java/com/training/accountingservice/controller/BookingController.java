package com.training.accountingservice.controller;

import com.training.accountingservice.model.BookingEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @PostMapping
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingEntity booking) {
        return ResponseEntity.ok("Booking is valid");
    }
}
