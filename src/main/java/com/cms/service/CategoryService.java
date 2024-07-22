package com.cms.service;

import com.cms.dto.response.CategoryDto;
import com.cms.dto.DtoMapper;
import com.cms.entity.Category;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(DtoMapper.CATEGORY_TO_DTO).collect(Collectors.toList());
    }

    public Category getCategoryBySlug(String slug) {
        Category search = new Category();
        search.setSlug(slug);
        Optional<Category> optionalCategory = categoryRepository.findOne(Example.of(search));
        return optionalCategory.orElseThrow();
    }
}
