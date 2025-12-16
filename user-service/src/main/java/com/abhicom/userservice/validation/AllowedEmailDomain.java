package com.abhicom.userservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedEmailDomainValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedEmailDomain {

    String message() default "email domain is not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // allowed domains list (configurable per field usage)
    String[] allowed();
}
