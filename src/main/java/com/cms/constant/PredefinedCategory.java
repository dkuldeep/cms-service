package com.cms.constant;

public enum PredefinedCategory {
    UNCATEGORIZED("Uncategorized", "uncategorized"),
    BLOG("-- Blog --", "blog"),
    PAGES("-- Pages --", "pages");

    private final String label;
    private final String slug;

    PredefinedCategory(String label, String slug) {
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
