package com.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Tag extends Webpage {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column
    private String title;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>(0);

    @ManyToMany(mappedBy = "tags")
    private Set<Tool> tools = new HashSet<>(0);

    @ManyToMany(mappedBy = "tags")
    private Set<Tool> blogs = new HashSet<>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Tool> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Tool> blogs) {
        this.blogs = blogs;
    }
}
