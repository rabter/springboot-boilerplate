package com.namutech.spero.common.exception;

public class CustomInvalidConfigGroupException extends RuntimeException {

    public CustomInvalidConfigGroupException(String message) {
        super(message);
    }

    public CustomInvalidConfigGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
