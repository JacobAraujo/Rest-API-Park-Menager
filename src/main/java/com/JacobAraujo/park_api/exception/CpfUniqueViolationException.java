package com.JacobAraujo.park_api.exception;

public class CpfUniqueViolationException extends RuntimeException {
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
