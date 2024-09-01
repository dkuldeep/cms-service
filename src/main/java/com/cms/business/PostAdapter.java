package com.cms.business;

import com.cms.constant.PostType;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Tag;
import com.cms.repository.TagRepository;
import com.cms.service.ImageService;

import java.time.LocalDateTime;
import java.util.List;

public class PostAdapter implements PostRef {

    private final WordpressPost wordpressPost;
    private final CommonFieldsRef commonFieldsRef;

    public PostAdapter(WordpressPost wordpressPost,
                       ImageService imageService,
                       TagRepository tagRepository) {
        this.wordpressPost = wordpressPost;
        this.commonFieldsRef = new CommonFieldsImpl(wordpressPost, imageService, tagRepository);
    }

    @Override
    public String getHeading() {
        return wordpressPost.getTitle().getRendered();
    }

    @Override
    public PostType getType() {
        return PostType.UNCATEGORIZED;
    }

    @Override
    public String getExcerpt() {
        return getDescription();
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
