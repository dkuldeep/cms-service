package com.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageDto {
    @JsonProperty
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
