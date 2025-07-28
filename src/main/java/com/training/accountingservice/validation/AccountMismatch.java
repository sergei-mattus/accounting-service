package com.training.accountingservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented // Zeigt Annotation in Javadoc an
@Constraint(validatedBy = AccountMismatchValidator.class) // 	Verknüpft Annotation mit Validator-Klasse
@Target({ ElementType.TYPE }) // Definiert, wo Annotation verwendet werden darf (z.B. auf Klassen)
@Retention(RetentionPolicy.RUNTIME) // Macht Annotation zur Laufzeit sichtbar – notwendig für Bean Validation
public @interface AccountMismatch {
    String message() default "Account and contraAccount must not be the same"; // 	Die Fehlermeldung für die Annotation
    Class<?>[] groups() default {}; // Erlaubt Validierungsgruppen (z.B. Create vs. Update)
    Class<? extends Payload>[] payload() default {}; // Erweiterbar für Custom-Metadaten
}
