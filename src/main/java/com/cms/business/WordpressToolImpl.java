package com.cms.business;

import com.cms.constant.ToolType;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Tag;
import com.cms.repository.TagRepository;
import com.cms.service.ImageService;

import java.util.List;

public class WordpressToolImpl implements ToolRef {

    private final WordpressPost wordpressPost;
    private final CommonFieldsRef commonFieldsRef;

    public WordpressToolImpl(WordpressPost wordpressPost, ImageService imageService, TagRepository tagRepository) {
        this.wordpressPost = wordpressPost;
        this.commonFieldsRef = new CommonFieldsImpl(wordpressPost, imageService, tagRepository);
    }

    @Override
    public String getName() {
        return wordpressPost.getSlug().replaceAll("-", " ");
    }

    @Override
    public String getType() {
        return ToolType.UNCATEGORIZED.name();
    }

    @Override
    public String getSlug() {
        return commonFieldsRef.getSlug();
    }

    @Override
    public String getTitle() {
        return commonFieldsRef.getTitle();
    }

    @Override
    public String getDescription() {
        return commonFieldsRef.getDescription();
    }

    @Override
    public String getContent() {
        return commonFieldsRef.getContent();
    }

    @Override
    public String getExcerpt() {
        return commonFieldsRef.getExcerpt();
    }

    @Override
    public List<Tag> getTags() {
        return commonFieldsRef.getTags();
    }
}
