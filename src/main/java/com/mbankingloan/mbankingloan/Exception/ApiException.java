package com.mbankingloan.mbankingloan.Exception;



import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


// Handle validation
@RestControllerAdvice
public class ApiException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<Map<String, String>> ErrorDetailResponse = new ArrayList<>();
        e.getFieldErrors().forEach(fieldError -> {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("fieldError", fieldError.getField());
            errorDetail.put("Reason", fieldError.getDefaultMessage());
            errorDetail.put("RejectedValue", Objects.requireNonNull(fieldError.getRejectedValue()).toString());
            ErrorDetailResponse.add(errorDetail);
        });
        ErrorDetailsResponse<?> errorDetailsResponse = ErrorDetailsResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .title(e.getTitleMessageCode())
                .details(ErrorDetailResponse)
                .build();
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(errorDetailsResponse)
                .build(), e.getStatusCode());
    }


    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleMethodRuntimeException(ResponseStatusException e) {

        ErrorDetailsResponse<?> errorDetailsResponse = ErrorDetailsResponse.builder()
                .code(e.getTitleMessageCode())
                .title(e.getMessage())
                .details(e.getDetailMessageCode())
                .build();

        return new ResponseEntity<>(ErrorResponse.builder()
                .error(errorDetailsResponse)
                .build(), e.getStatusCode());
    }

}
