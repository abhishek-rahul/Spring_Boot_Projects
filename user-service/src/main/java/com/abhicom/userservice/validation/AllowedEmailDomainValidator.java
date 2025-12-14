package com.abhicom.userservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class AllowedEmailDomainValidator implements ConstraintValidator<AllowedEmailDomain, String> {

    private String[] allowedDomains;

    @Override
    public void initialize(AllowedEmailDomain constraintAnnotation) {
        this.allowedDomains = constraintAnnotation.allowed();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // If value is blank/null, let @NotBlank handle it (we return true here)
        if (value == null || value.isBlank()) return true;

        int at = value.lastIndexOf('@');
        if (at < 0 || at == value.length() - 1) return true; // let @Email handle invalid email shape

        String domain = value.substring(at + 1).toLowerCase();

        return Arrays.stream(allowedDomains)
                .anyMatch(d -> d.equalsIgnoreCase(domain));
    }
}
