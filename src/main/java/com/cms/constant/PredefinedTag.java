package com.cms.constant;

public enum PredefinedTag {
    WORDPRESS_IMPORT("Wordpress Import", "wordpress-import");

    private final String label;
    private final String slug;

    PredefinedTag(String label, String slug) {
        this.label = label;
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public String getSlug() {
        return slug;
    }
}
