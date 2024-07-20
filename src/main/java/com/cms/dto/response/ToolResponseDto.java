package com.cms.dto.response;

import com.cms.dto.AbstractCategoryResponse;
import com.cms.dto.ToolTypeDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collections;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ToolResponseDto extends AbstractCategoryResponse {

    private ToolTypeDto type;
    private String content;
    private String tagline;
    private List<TagDto> tags = Collections.emptyList();

    public ToolTypeDto getType() {
        return type;
    }

    public void setType(ToolTypeDto type) {
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
}
