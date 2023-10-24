package com.example.demo1.entities.categories;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidCategoryValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface ValidCategory {

    String message() default
            "You provided a non-existent category!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
