package com.cms.business;

import com.cms.entity.Category;

import java.time.LocalDateTime;

public interface PostRef extends CommonFieldsRef {
    String getHeading();

    LocalDateTime getCreatedDate();

    LocalDateTime getUpdatedDate();

    Category getCategory();
}
