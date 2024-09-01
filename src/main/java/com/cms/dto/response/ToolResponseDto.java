package com.cms.dto.response;

import com.cms.dto.AbstractTagResponse;
import com.cms.dto.TypeDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collections;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ToolResponseDto extends AbstractTagResponse {

    private TypeDto type;
    private String content;
    private String tagline;
    private List<TagDto> tags = Collections.emptyList();
    private List<PostSnippetDto> relatedBlogs = Collections.emptyList();

    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public List<PostSnippetDto> getRelatedBlogs() {
        return relatedBlogs;
    }

    public void setRelatedBlogs(List<PostSnippetDto> relatedBlogs) {
        this.relatedBlogs = relatedBlogs;
    }
}
