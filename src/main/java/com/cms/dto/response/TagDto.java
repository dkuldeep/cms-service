package com.cms.dto.response;

import com.cms.dto.AbstractTagResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collections;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TagDto extends AbstractTagResponse {

    @JsonProperty
    private Integer count;

    private List<PostSnippetDto> relatedPosts = Collections.emptyList();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PostSnippetDto> getRelatedPosts() {
        return relatedPosts;
    }

    public void setRelatedPosts(List<PostSnippetDto> relatedPosts) {
        this.relatedPosts = relatedPosts;
    }
}
