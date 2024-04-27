package com.cms.controller;

import com.cms.dto.CategoryDto;
import com.cms.dto.DtoMapper;
import com.cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(DtoMapper.CATEGORY_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping("")
    public void addCategory(@RequestBody CategoryDto categoryDto) {
        categoryRepository.saveAndFlush(DtoMapper.DTO_TO_CATEGORY.apply(categoryDto));
    }
}
