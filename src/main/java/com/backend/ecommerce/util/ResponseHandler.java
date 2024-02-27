package com.backend.ecommerce.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.function.Supplier;

@Component
public class ResponseHandler {

    public static <T> ResponseEntity<ResponseData<T>> handleRequest(Supplier<T> action) {
        return handleRequest(action, null);
    }

    public static <T> ResponseEntity<ResponseData<T>> handleRequest(Supplier<T> action, Errors errors) {
        ResponseData<T> responseData = new ResponseData<>();
        try {
            if (errors != null && errors.hasErrors()) {
                handleValidationErrors(errors, responseData);
            } else {
                responseData.setStatus(true);
                responseData.getMessage().add("Success");
                responseData.setPayload(action.get());
                return ResponseEntity.ok(responseData);
            }
        } catch (Exception e) {
            handlException(e, responseData);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }

    private static void handleValidationErrors(Errors errors, ResponseData<?> responseData) {
        errors.getAllErrors().forEach(error -> responseData.getMessage().add(error.getDefaultMessage()));
        responseData.setStatus(false);
        responseData.setPayload(null);
    }

    private static void handlException(Exception e, ResponseData<?> responseData) {
        responseData.setStatus(false);
        responseData.getMessage().clear();
        responseData.getMessage().add(e.getMessage());
    }
}
