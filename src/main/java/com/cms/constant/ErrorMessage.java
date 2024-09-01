package com.cms.constant;

import java.text.MessageFormat;
import java.util.Arrays;

public class ErrorMessage {
    public static final String POST_BY_ID_NOT_FOUND = "Post not found with id: %d";
    public static final String TAG_NOT_FOUND_WITH_ID = "Tag not found with id: %s";
    public static final String POST_NOT_FOUND_WITH_SLUG = "Post not found with slug {0}";
    //
    public static final String TAG_CREATED = "Tag Created Successfully";
    public static final String TAG_UPDATED = "Tag Updated";
    public static final String TAG_NOT_FOUND_WITH_SLUG = "Tag not found with slug {0}";
    //
    public static final String POST_CREATED = "Post Created Successfully";
    public static final String POST_UPDATED = "Post Updated";
    //
    public static final String TOOl_CREATED = "Tool Created Successfully";
    public static final String TOOL_UPDATED = "Tool Updated";
    public static final String TOOL_NOT_FOUND_WITH_ID = "Tool not found with id {0}";
    public static final String TOOL_NOT_FOUND_WITH_SLUG = "Tool not found with slug {0}";

    public static final String OBJECT_ALREADY_EXIST_WITH_SLUG = "{0} already exists with slug {1}";

    public static void main(String[] args) {
        System.out.println(MessageFormat.format(TOOL_NOT_FOUND_WITH_ID, 5));
        System.out.println(Arrays.stream(ToolType.values()).toList().contains(""));
    }
}
