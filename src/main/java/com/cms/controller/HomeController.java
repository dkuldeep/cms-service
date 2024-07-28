package com.cms.controller;

import com.cms.constant.DefaultCategory;
import com.cms.entity.Category;
import com.cms.entity.Tag;
import com.cms.repository.CategoryRepository;
import com.cms.repository.TagRepository;
import com.cms.service.CategoryService;
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
    private CategoryService categoryService;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String homePage() {
        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("add-default")
    @ResponseStatus(HttpStatus.OK)
    public void addDefaultSettings() {
        for (DefaultCategory category : DefaultCategory.values()) {
            Optional<Category> optionalCategory = categoryService.findBySlug(category.getSlug());
            if (optionalCategory.isEmpty()) {
                Category category1 = new Category();
                category1.setSlug(category.getSlug());
                category1.setName(category.getLabel());
                category1.setCreated(LocalDateTime.now());
                categoryRepository.save(category1);
            } else {
                Category existing = optionalCategory.get();
                existing.setName(category.getLabel());
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
