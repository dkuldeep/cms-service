package com.cms.constant;

public enum ObjectType {
    TOOL("Tool"),
    POST("Post"),
    CATEGORY("Category"),
    TAG("Tag");

    private final String label;

    ObjectType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
