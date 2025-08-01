package com.training.accountingservice.repository;

import com.training.accountingservice.model.BookingEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class BookingRepository {

    private final Map<UUID, BookingEntity> bookings = new HashMap<>();

    public BookingEntity save(BookingEntity booking) {
        bookings.put(booking.getId(), booking);
        return booking;
    }
}
