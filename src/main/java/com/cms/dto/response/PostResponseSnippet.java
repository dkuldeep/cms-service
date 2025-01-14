package com.cms.dto.response;

import com.cms.dto.TypeDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PostResponseSnippet {
    private String image;
    private String heading;
    private String slug;
    private String excerpt;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<TagResponseSnippet> tags = Collections.emptyList();
    private TypeDto type;
    private Integer id;
    private String description;

    public PostResponseSnippet() {
    }

    public PostResponseSnippet(PostResponseSnippet other) {
        this.image = other.image;
        this.heading = other.heading;
        this.slug = other.slug;
        this.excerpt = other.excerpt;
        this.created = other.created;
        this.tags = other.tags;
        this.type = other.type;
        this.id = other.id;
        this.description = other.description;
        this.updated = other.updated;
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

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public List<TagResponseSnippet> getTags() {
        return tags;
    }

    public void setTags(List<TagResponseSnippet> tags) {
        this.tags = tags;
    }
    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
