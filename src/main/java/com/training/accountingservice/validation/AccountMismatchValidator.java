package com.training.accountingservice.validation;

import com.training.accountingservice.model.AccountingEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountMismatchValidator implements ConstraintValidator<AccountMismatch, AccountingEntity> {

    @Override
    public boolean isValid(AccountingEntity accounting, ConstraintValidatorContext context) {
        if (accounting == null) return true;
        if (accounting.getAccount() == null || accounting.getContraAccount() == null) return true;

        return !accounting.getAccount().equals(accounting.getContraAccount());
    }
}
