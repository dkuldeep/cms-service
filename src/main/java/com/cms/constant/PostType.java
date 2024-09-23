package com.cms.constant;

public enum PostType {
    UNCATEGORIZED("Uncategorized"),
    BLOG("Blog"),
    PAGE("Page");

    private final String label;

    PostType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
