package com.namutech.spero.common.exception;

public class InvalidConfigGroupException extends RuntimeException {

    public InvalidConfigGroupException(String message) {
        super(message);
    }

    public InvalidConfigGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
