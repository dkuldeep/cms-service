package com.cms.dto.response;

public class PostSnippetDto {
    private String slug;
    private String heading;
    private CategorySnippetDto category;

    public PostSnippetDto(String slug, String heading, CategorySnippetDto category) {
        this.slug = slug;
        this.heading = heading;
        this.category = category;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public CategorySnippetDto getCategory() {
        return category;
    }

    public void setCategory(CategorySnippetDto category) {
        this.category = category;
    }
}
