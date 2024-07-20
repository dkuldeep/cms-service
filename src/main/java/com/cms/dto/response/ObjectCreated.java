package com.cms.dto.response;

public class ObjectCreated extends ObjectUpdated {
    private Integer id;

    public ObjectCreated(Integer id, String message) {
        super(message);
        this.id = id;
    }

    public ObjectCreated(String message) {
        super(message);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
