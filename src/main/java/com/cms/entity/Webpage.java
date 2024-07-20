package com.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@MappedSuperclass
public class Webpage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Integer id;

    @Column(unique = true, nullable = false)
    @NotBlank
    protected String slug;

    @Column
    protected String description;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime created;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime updated;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

}
