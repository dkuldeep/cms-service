package com.cms.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collections;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ToolResponse extends ToolResponseSnippet {
    private String content;
    private List<PostResponseSnippet> relatedBlogs = Collections.emptyList();

    public ToolResponse(ToolResponseSnippet snippet) {
        super(snippet);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PostResponseSnippet> getRelatedBlogs() {
        return relatedBlogs;
    }

    public void setRelatedBlogs(List<PostResponseSnippet> relatedBlogs) {
        this.relatedBlogs = relatedBlogs;
    }


}
