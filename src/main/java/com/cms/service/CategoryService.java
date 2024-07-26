package com.cms.service;

import com.cms.entity.Category;
import com.cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<Category> findBySlug(String slug) {
        Category search = new Category();
        search.setSlug(slug);
        return categoryRepository.findOne(Example.of(search));
    }
}
