package com.cms.dto.response;

import com.cms.dto.AbstractPostResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostResponseDto extends AbstractPostResponse {

    private CategoryDto category;
    private List<PostSnippetDto> related = new ArrayList<>(0);

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public List<PostSnippetDto> getRelated() {
        return related;
    }

    public void setRelated(List<PostSnippetDto> related) {
        this.related = related;
    }
}
