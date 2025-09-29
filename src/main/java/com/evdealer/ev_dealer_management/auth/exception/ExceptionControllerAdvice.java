package com.evdealer.ev_dealer_management.auth.exception;

import com.evdealer.ev_dealer_management.utils.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();

        ErrorDto errorDto = new ErrorDto(
                "400",
                "Validation Error",
                "Request has invalid fields",
                fieldErrors
        );
        return ResponseEntity.badRequest().body(errorDto);
    }

}
