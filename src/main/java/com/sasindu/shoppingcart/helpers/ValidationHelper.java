package com.sasindu.shoppingcart.helpers;

import com.sasindu.shoppingcart.exceptions.BadRequestException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Map;
import java.util.HashMap;

public class ValidationHelper {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static void validateModelBinding(Object objectToValidate) throws BadRequestException {
        if (objectToValidate == null) {
            throw new BadRequestException("Request body is empty");
        }

        var violations = validator.validate(objectToValidate);

        if (!violations.isEmpty()) {
            Map<String, String> errorMap = new HashMap<>();
            for (var violation : violations) {
                errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new BadRequestException("Validation failed -> " + formatErrors(errorMap));
        }
    }

    private static String formatErrors(Map<String, String> errorMap) {
        StringBuilder errorMessage = new StringBuilder();
        for (Map.Entry<String, String> entry : errorMap.entrySet()) {
            errorMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append("; ");
        }
        return errorMessage.toString().trim();
    }
}
