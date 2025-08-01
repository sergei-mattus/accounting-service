package com.training.accountingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.training.accountingservice.model.BookingEntity;
import com.training.accountingservice.model.BookingRequest;
import com.training.accountingservice.model.BookingResponse;
import com.training.accountingservice.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
@WebMvcTest startet einen Spring-Kontext, der nur den Web-Layer lädt – also Controller, ControllerAdvice, Filter, Jackson, usw.
Alles andere wie Services, Repositories, Datenbanken werden nicht geladen.

„Für einfache, schnelle Unit-Tests setze ich auf MockMvcBuilders.standaloneSetup() – da habe ich volle Kontrolle und keinen Overhead.
Wenn ich das Verhalten der Web-Schicht realistisch testen will – inklusive Validierung und Fehlerbehandlung –, nutze ich @WebMvcTest mit @MockBean.
 */
@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturn400WhenInvalidBookingIsPosted() throws Exception {
        // given
        BookingEntity invalidBooking = new BookingEntity(
                null,
                null,
                "",
                new BigDecimal("-1"),
                "",
                ""
        );

        // when & then
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBooking)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBookingResponse() throws Exception {
        // given
        BookingRequest request = new BookingRequest(
                LocalDate.now(),
                "test",
                new BigDecimal("99.99"),
                "1000",
                "2000"
        );

        UUID generatedId = UUID.randomUUID();
        BookingResponse response = new BookingResponse(
                generatedId,
                request.bookingDate(),
                request.description(),
                request.amount(),
                request.account(),
                request.contraAccount()
        );

        when(bookingService.createBooking(any())).thenReturn(response);

        // when & then
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(generatedId.toString()))
                .andExpect(jsonPath("$.amount").value(99.99))
                .andExpect(jsonPath("$.account").value("1000"))
                .andExpect(jsonPath("$.description").value("test"));
    }
}
