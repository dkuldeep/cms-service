package com.cms.dto.response;

public class ObjectUpdated {
    private String message;

    public ObjectUpdated(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
