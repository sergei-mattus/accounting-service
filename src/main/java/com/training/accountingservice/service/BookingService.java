package com.training.accountingservice.service;

import com.training.accountingservice.model.BookingRequest;
import com.training.accountingservice.model.BookingEntity;
import com.training.accountingservice.model.BookingResponse;
import com.training.accountingservice.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingResponse createBooking(BookingRequest request) {
        UUID id = UUID.randomUUID();

        BookingEntity booking = BookingEntity.builder()
                .id(id)
                .bookingDate(request.bookingDate())
                .description(request.description())
                .amount(request.amount())
                .account(request.account())
                .contraAccount(request.contraAccount())
                .build();

        BookingEntity saved = bookingRepository.save(booking);

        return new BookingResponse(
                saved.getId(),
                saved.getBookingDate(),
                saved.getDescription(),
                saved.getAmount(),
                saved.getAccount(),
                saved.getContraAccount()
        );
    }
}
