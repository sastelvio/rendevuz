package com.sastelvio.rendezvous.exception;

import lombok.Getter;

@Getter
public class CustomExceptionResponse {
    private final String message;

    public CustomExceptionResponse(String message) {
        this.message = message;
    }
}