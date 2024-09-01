package com.cms.business;

import com.cms.constant.PostType;

public interface PostRef extends CommonFieldsRef {
    String getHeading();

    String getExcerpt();

    PostType getType();
}
