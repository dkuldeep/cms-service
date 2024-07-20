package com.cms.business;

import com.cms.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

public interface CommonFieldsRef {
    String getSlug();

    String getDescription();

    LocalDateTime getCreated();

    LocalDateTime getUpdated();

    String getContent();

    List<Tag> getTags();
}
