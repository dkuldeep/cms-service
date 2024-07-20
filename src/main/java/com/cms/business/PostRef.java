package com.cms.business;

import com.cms.entity.Category;

public interface PostRef extends CommonFieldsRef {
    String getHeading();

    String getExcerpt();

    Category getCategory();
}
