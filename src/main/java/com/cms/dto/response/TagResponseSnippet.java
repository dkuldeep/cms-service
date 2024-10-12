package com.cms.dto.response;

public class TagResponseSnippet {
    private Integer id;
    private String name;
    private String slug;
    private String title;
    private String description;
    private Integer count;

    public TagResponseSnippet() {
    }

    public TagResponseSnippet(TagResponseSnippet that) {
        this.id = that.id;
        this.name = that.name;
        this.slug = that.slug;
        this.title = that.title;
        this.description = that.description;
        this.count = that.count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
