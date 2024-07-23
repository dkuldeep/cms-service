package com.cms.controller;

import com.cms.constant.PredefinedCategory;
import com.cms.entity.Category;
import com.cms.entity.Tag;
import com.cms.repository.CategoryRepository;
import com.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cms.constant.PredefinedTag.WORDPRESS_IMPORT;

@RestController
@RequestMapping("")
public class HomeController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String homePage() {
        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("add-default")
    @ResponseStatus(HttpStatus.OK)
    public void addDefaultSettings() {
        for (PredefinedCategory category : PredefinedCategory.values()) {
            Category search = new Category();
            search.setSlug(category.getSlug());
            Optional<Category> optional = categoryRepository.findOne(Example.of(search));
            if (optional.isEmpty()) {
                Category category1 = new Category();
                category1.setSlug(category.getSlug());
                category1.setName(category.getLabel());
                category1.setCreated(LocalDateTime.now());
                categoryRepository.save(category1);
            }
        }
        categoryRepository.flush();
        Tag search = new Tag();
        search.setSlug(WORDPRESS_IMPORT.getSlug());
        Optional<Tag> optional = tagRepository.findOne(Example.of(search));
        if (optional.isEmpty()) {
            Tag tag = new Tag();
            tag.setName(WORDPRESS_IMPORT.getLabel());
            tag.setSlug(WORDPRESS_IMPORT.getSlug());
            tag.setCreated(LocalDateTime.now());
            tagRepository.saveAndFlush(tag);
        }
    }
}
