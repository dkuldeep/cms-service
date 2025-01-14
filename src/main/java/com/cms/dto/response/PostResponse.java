package com.cms.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostResponse extends PostResponseSnippet {

    private String content;
    private List<PostResponseSnippet> related = new ArrayList<>(0);

    public PostResponse(PostResponseSnippet postResponseSnippet) {
        super(postResponseSnippet);
    }

    public List<PostResponseSnippet> getRelated() {
        return related;
    }

    public void setRelated(List<PostResponseSnippet> related) {
        this.related = related;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
