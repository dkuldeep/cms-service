package com.cms.dto.response;

import com.cms.dto.TypeDto;

import java.util.Collections;
import java.util.List;

public class ToolResponseSnippet {
    private Integer id;
    private String name;
    private String slug;
    private String title;
    private String description;
    private TypeDto type;
    private String tagline;
    private List<TagResponseSnippet> tags = Collections.emptyList();

    public ToolResponseSnippet() {
    }

    public ToolResponseSnippet(ToolResponseSnippet that) {
        this.id = that.id;
        this.name = that.name;
        this.slug = that.slug;
        this.title = that.title;
        this.description = that.description;
        this.type = that.type;
        this.tagline = that.tagline;
        this.tags = that.tags;
    }

    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<TagResponseSnippet> getTags() {
        return tags;
    }

    public void setTags(List<TagResponseSnippet> tags) {
        this.tags = tags;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
