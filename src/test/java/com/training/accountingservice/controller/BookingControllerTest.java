package com.training.accountingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.accountingservice.model.BookingEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
@WebMvcTest startet einen Spring-Kontext, der nur den Web-Layer lädt – also Controller, ControllerAdvice, Filter, Jackson, usw.
Alles andere wie Services, Repositories, Datenbanken werden nicht geladen.
 */
@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturn200WhenBookingIsPosted() throws Exception {
        // given
        BookingEntity validBooking = new BookingEntity(
                UUID.randomUUID(),
                LocalDate.now(),
                "test",
                new BigDecimal("99.99"),
                "1000",
                "2000"
        );

        // when & then
        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBooking)))
                .andExpect(status().isOk());
    }
}
