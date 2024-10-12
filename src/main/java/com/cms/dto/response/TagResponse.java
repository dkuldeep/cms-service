package com.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collections;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TagResponse extends TagResponseSnippet {

    private List<String> relatedTools = Collections.emptyList();
    private List<PostResponseSnippet> relatedPosts = Collections.emptyList();

    public TagResponse(TagResponseSnippet snippet) {
        super(snippet);
    }

    public List<PostResponseSnippet> getRelatedPosts() {
        return relatedPosts;
    }

    public void setRelatedPosts(List<PostResponseSnippet> relatedPosts) {
        this.relatedPosts = relatedPosts;
    }

    public List<String> getRelatedTools() {
        return relatedTools;
    }

    public void setRelatedTools(List<String> relatedTools) {
        this.relatedTools = relatedTools;
    }
}
