package com.training.accountingservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented // Zeigt Annotation in Javadoc an
@Constraint(validatedBy = AccountMismatchValidator.class) // 	Verknüpft Annotation mit Validator-Klasse
@Target({ ElementType.TYPE }) // Definiert, wo Annotation verwendet werden darf (z.B. auf Klassen)
@Retention(RetentionPolicy.RUNTIME) // Macht Annotation zur Laufzeit sichtbar – notwendig für Bean Validation
public @interface AccountMismatch {

    // 	Die Fehlermeldung für die Annotation
    String message() default "Account and contraAccount must not be the same";

    // Erlaubt Validierungsgruppen (z.B. Create vs. Update) - default, wenn man keine braucht
    Class<?>[] groups() default {};

    // Erweiterbar für Custom-Metadaten
    Class<? extends Payload>[] payload() default {};
}
