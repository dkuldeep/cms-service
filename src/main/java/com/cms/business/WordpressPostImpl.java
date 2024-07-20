package com.cms.business;

import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Category;
import com.cms.entity.Tag;
import com.cms.repository.CategoryRepository;
import com.cms.repository.TagRepository;
import com.cms.service.ImageService;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cms.constant.CommonConstants.WORDPRESS_IMPORT_DEFAULT_CATEGORY;

public class WordpressPostImpl implements PostRef {

    private final WordpressPost wordpressPost;
    private final CommonFieldsRef commonFieldsRef;
    private final CategoryRepository categoryRepository;

    public WordpressPostImpl(WordpressPost wordpressPost,
                             ImageService imageService,
                             TagRepository tagRepository,
                             CategoryRepository categoryRepository) {
        this.wordpressPost = wordpressPost;
        this.categoryRepository = categoryRepository;
        this.commonFieldsRef = new CommonFieldsImpl(wordpressPost, imageService, tagRepository);
    }

    @Override
    public String getHeading() {
        return wordpressPost.getTitle().getRendered();
    }

    @Override
    public Category getCategory() {
        Category search = new Category();
        search.setSlug(WORDPRESS_IMPORT_DEFAULT_CATEGORY);
        Optional<Category> optional = categoryRepository.findOne(Example.of(search));
        return optional.orElse(null);
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
