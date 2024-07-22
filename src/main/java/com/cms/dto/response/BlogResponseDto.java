package com.cms.dto.response;

import com.cms.dto.AbstractPostResponse;

import java.util.ArrayList;
import java.util.List;

public class BlogResponseDto extends AbstractPostResponse {
    private String image;
    private List<BlogSnippetDto> related = new ArrayList<>(0);

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<BlogSnippetDto> getRelated() {
        return related;
    }

    public void setRelated(List<BlogSnippetDto> related) {
        this.related = related;
    }
}
