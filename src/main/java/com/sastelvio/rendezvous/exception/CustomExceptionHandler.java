package com.sastelvio.rendezvous.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<CustomExceptionResponse> handleBusinessException(BusinessException e) {
        CustomExceptionResponse response = new CustomExceptionResponse(e.getMessage());
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY; // You can customize the HTTP status code here.
        return new ResponseEntity<>(response, httpStatus);
    }
}