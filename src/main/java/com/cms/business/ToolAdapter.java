package com.cms.business;

import com.cms.constant.ToolType;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Tag;
import com.cms.repository.TagRepository;
import com.cms.service.ImageService;

import java.time.LocalDateTime;
import java.util.List;

public class ToolAdapter implements ToolRef {

    private final WordpressPost wordpressPost;
    private final CommonFieldsRef commonFieldsRef;

    public ToolAdapter(WordpressPost wordpressPost,
                       ImageService imageService,
                       TagRepository tagRepository) {
        this.wordpressPost = wordpressPost;
        this.commonFieldsRef = new CommonFieldsImpl(wordpressPost, imageService, tagRepository);
    }

    @Override
    public String getName() {
        return wordpressPost.getSlug().replaceAll("-", " ");
    }

    @Override
    public String getTitle() {
        return wordpressPost.getTitle().getRendered();
    }

    @Override
    public String getType() {
        return ToolType.UNCATEGORIZED.name();
    }

    @Override
    public String getTagline() {
        return "";
    }

    @Override
    public String getSlug() {
        return commonFieldsRef.getSlug();
    }

    @Override
    public String getDescription() {
        return commonFieldsRef.getDescription();
    }

    @Override
    public LocalDateTime getCreated() {
        return commonFieldsRef.getCreated();
    }

    @Override
    public LocalDateTime getUpdated() {
        return commonFieldsRef.getUpdated();
    }

    @Override
    public String getContent() {
        return commonFieldsRef.getContent();
    }

    @Override
    public List<Tag> getTags() {
        return commonFieldsRef.getTags();
    }
}
