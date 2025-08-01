package com.training.accountingservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookingResponse(@NotNull UUID id,
                              @NotNull LocalDate bookingDate,
                              @NotBlank String description,
                              @NotNull @Positive BigDecimal amount,
                              @NotBlank String account,
                              @NotBlank String contraAccount) {
}
