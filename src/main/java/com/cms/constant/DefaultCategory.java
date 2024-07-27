package com.cms.constant;

import java.util.Arrays;
import java.util.List;

public enum DefaultCategory {
    UNCATEGORIZED("-- Uncategorized --", "uncategorized"),
    BLOG("-- Blog --", "blog"),
    PAGES("-- Pages --", "pages");

    private final String label;
    private final String slug;

    DefaultCategory(String label, String slug) {
        this.label = label;
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public String getSlug() {
        return slug;
    }

    public static List<String> getAllSlugs() {
        return Arrays.stream(values()).map(DefaultCategory::getSlug).toList();
    }
}
