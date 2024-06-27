package com.cms.dto;

public class ObjectNotFoundDto {
    private final String message;

    public ObjectNotFoundDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
