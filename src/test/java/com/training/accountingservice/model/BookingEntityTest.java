package com.training.accountingservice.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
"@SpringBootTest verwende ich, wenn ich echte Spring-Funktionalität im Test brauche –
z.B. für Autowiring, Bean Validation oder Integration von Service + Repository.
Für einfache Unit-Tests reicht aber oft ein schlanker Setup ohne Spring-Kontext."
 */
public class BookingEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWhenBookingIsValid() {
        // given
        BookingEntity accounting = new BookingEntity(
                UUID.randomUUID(),
                LocalDate.now(),
                "Valid booking",
                new BigDecimal("50.00"),
                "1000",
                "2000"
        );

        // when
        Set<ConstraintViolation<BookingEntity>> violations = validator.validate(accounting);

        // then
        assertTrue(violations.isEmpty());
    }


    @Test
    void shouldFailValidationWhenBookingIsInvalid() {
        // given
        BookingEntity accounting = new BookingEntity(
                null,
                null,
                "",
                new BigDecimal("-100"),
                "",
                ""
        );

        // when
        Set<ConstraintViolation<BookingEntity>> violations = validator.validate(accounting);
        violations.forEach(v ->
                System.out.println(v.getPropertyPath() + " - " + v.getMessage())
        );

        // then
        assertEquals(7, violations.size());
    }
}