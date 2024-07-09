package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.CategoryDto;
import com.cms.dto.DtoMapper;
import com.cms.entity.Category;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(DtoMapper.CATEGORY_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryRepository.saveAndFlush(DtoMapper.DTO_TO_CATEGORY.apply(categoryDto));
        return DtoMapper.CATEGORY_TO_DTO.apply(category);
    }

    @Transactional
    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setName(categoryDto.getName());
            category.setSlug(categoryDto.getSlug());
            category.setDescription(categoryDto.getDescription());
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_ID, id));
        }
    }

    @DeleteMapping("/{id}")
    public void getCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }
}
