package com.cms.dto.response;

import java.time.LocalDateTime;

public class PostSnippetDto {
    private String slug;
    private String heading;
    private CategorySnippetDto category;
    private LocalDateTime created;
    private String image;

    public PostSnippetDto(String slug,
                          String heading,
                          CategorySnippetDto category,
                          LocalDateTime created,
                          String image) {
        this.slug = slug;
        this.heading = heading;
        this.category = category;
        this.created = created;
        this.image = image;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
