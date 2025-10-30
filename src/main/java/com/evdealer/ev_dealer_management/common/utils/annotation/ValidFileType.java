package com.evdealer.ev_dealer_management.common.utils.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// Bean Validation Annotation Custom
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeValidator.class)
public @interface ValidFileType {

    String message() default "Invalid file type";
    String[] allowedTypes();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
