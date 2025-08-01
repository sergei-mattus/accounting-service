package com.training.accountingservice.service;

import com.training.accountingservice.model.BookingEntity;
import com.training.accountingservice.model.BookingRequest;
import com.training.accountingservice.model.BookingResponse;
import com.training.accountingservice.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    /*
    „Ich verwende @Captor, um gezielt zu prüfen, welches Objekt mein Service an ein Repository
    oder einen anderen Mock übergibt. So kann ich sauber verifizieren, ob alle Felder korrekt gesetzt wurden –
    gerade bei Mapping oder ID-Erzeugung. Es macht den Test präziser als any().“
     */
    @Captor
    private ArgumentCaptor<BookingEntity> bookingCaptor;

    @Test
    void shouldCreateBookingCorrectly() {
        // given
        BookingRequest dto = new BookingRequest(
                LocalDate.of(1, 1, 1),
                "test-description",
                new BigDecimal("50.00"),
                "1000",
                "2000"
        );

        // Damit wird getestet, ob die Ausgabe exakt gleich ist wie die Eingabe
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        BookingResponse result = bookingService.createBooking(dto);

        // then
        verify(bookingRepository).save(bookingCaptor.capture());
        BookingEntity captured = bookingCaptor.getValue();

        assertEquals(result.id(), captured.getId());
        assertEquals(dto.bookingDate(), captured.getBookingDate());
        assertEquals(dto.description(), captured.getDescription());
        assertEquals(dto.amount(), captured.getAmount());
        assertEquals(dto.account(), captured.getAccount());
        assertEquals(dto.contraAccount(), captured.getContraAccount());
    }

}