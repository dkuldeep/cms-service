package com.cms.exception;

public class ObjectAlreadyExistException extends RuntimeException {
    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
