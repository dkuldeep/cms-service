package com.cms.dto.response;

import java.time.LocalDateTime;

public class BlogSnippetDto {
    private String image;
    private LocalDateTime created;
    private String heading;
    private String slug;

    public BlogSnippetDto(String image, LocalDateTime created, String heading, String slug) {
        this.image = image;
        this.created = created;
        this.heading = heading;
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
