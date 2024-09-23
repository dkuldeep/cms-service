package com.cms.entity;

import com.cms.constant.ToolType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Tool extends Webpage {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column
    private String title;

    @Column(columnDefinition = "VARCHAR(30)")
    @Enumerated(value = EnumType.STRING)
    private ToolType type;

    @Column(length = Integer.MAX_VALUE)
    private String content;

    @Column
    private String tagline;

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "tool_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>(0);

//    ------------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToolType getType() {
        return type;
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
