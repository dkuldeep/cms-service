package com.cms.entity;

import com.cms.constant.PostType;
import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Post extends Webpage {

    @Column
    @NotEmpty
    private String heading;

    @Column
    private String excerpt;

    @Column(columnDefinition = "VARCHAR(30)")
    @Enumerated(value = EnumType.STRING)
    private PostType type;

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>(0);

    @Column(length = Integer.MAX_VALUE)
    private String content;

    @Column
    private String image;

//    ------------------------------------------------------------------------------------------------------------------

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
