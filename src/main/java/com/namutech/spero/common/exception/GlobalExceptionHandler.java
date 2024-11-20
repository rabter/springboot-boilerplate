package com.namutech.spero.common.exception;

import com.namutech.spero.common.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomInvalidConfigGroupException.class)
    public ResponseEntity<ApiErrorResponse<?>> handleCustomInvalidConfigGroupException(CustomInvalidConfigGroupException ex) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok().body(apiErrorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok().body(apiErrorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse<?>> handleRuntimeException(RuntimeException ex) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok().body(apiErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse<?>> handleException(Exception ex) {
        ApiErrorResponse<?> apiErrorResponse = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.ok().body(apiErrorResponse);
    }
}
