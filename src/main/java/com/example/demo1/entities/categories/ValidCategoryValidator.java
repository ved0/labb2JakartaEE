package com.example.demo1.entities.categories;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ValidCategoryValidator implements ConstraintValidator<ValidCategory, Category> {

    @Override
    public boolean isValid(Category s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            return Category.isValidCategory(s.name()) && !("non_existent".equalsIgnoreCase(s.name()));
        }
    }
}
