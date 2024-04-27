package com.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table
public class Tag extends Webpage {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
