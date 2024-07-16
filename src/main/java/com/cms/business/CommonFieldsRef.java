package com.cms.business;

import com.cms.entity.Tag;

import java.util.List;

public interface CommonFieldsRef {
    String getSlug();

    String getTitle();

    String getDescription();

    String getContent();

    String getExcerpt();

    List<Tag> getTags();
}
