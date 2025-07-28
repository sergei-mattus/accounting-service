package com.training.accountingservice.model;

import com.training.accountingservice.validation.AccountMismatch;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/*
„Im Alltag nutze ich oft Lomboks @Builder, gerade für Objekte mit vielen Feldern. H
Hier im Interview schreibe ich es explizit aus – um die Logik klar zu zeigen.“
 */

/*
„Wenn ich eine Regel habe, die ich direkt am Modell abbilden kann – wie z.B.
'Konto und Gegenkonto dürfen nicht gleich sein' –, würde ich eher eine eigene Annotation erstellen.
Wenn aber mehr Kontext nötig ist – z.B. Abfragen aus der DB oder Feature-Toggles –,
dann würde ich die Validierung als Methode im Service schreiben. Ich achte immer darauf,
was fachlich Sinn ergibt und was später wartbar bleibt.“
 */
@Entity
@AccountMismatch
public class AccountingEntity {

    @Id
    @NotNull
    private UUID id;

    @NotNull
    private LocalDate bookingDate;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String account;

    @NotBlank
    private String contraAccount;


    public AccountingEntity(UUID id, LocalDate bookingDate, String description,
                            BigDecimal amount, String account, String contraAccount) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.description = description;
        this.amount = amount;
        this.account = account;
        this.contraAccount = contraAccount;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccount() {
        return account;
    }

    public String getContraAccount() {
        return contraAccount;
    }
}
