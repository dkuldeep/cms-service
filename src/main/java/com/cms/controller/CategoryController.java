package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.request.CategoryCreateRequest;
import com.cms.dto.response.CategoryDto;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.entity.Category;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(required = false) String slug) {
        List<Category> categories;
        if (Objects.nonNull(slug)) {
            Category category = new Category();
            category.setSlug(slug);
            categories = categoryRepository.findAll(Example.of(category));
        } else {
            categories = categoryRepository.findAll();
        }
        return categories.stream().map(DtoMapper.CATEGORY_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping
    public ObjectCreated addCategory(@RequestBody CategoryCreateRequest request) {
        Category category = new Category();
        mapRequestToCategory(request, category);
        category.setCreated(LocalDateTime.now());
        category = categoryRepository.saveAndFlush(category);
        return new ObjectCreated(category.getId(), ErrorMessage.CATEGORY_CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ObjectUpdated updateCategory(@PathVariable Integer id, @RequestBody CategoryCreateRequest request) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            mapRequestToCategory(request, category);
            category.setUpdated(LocalDateTime.now());
            return new ObjectUpdated(ErrorMessage.CATEGORY_UPDATED);
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_ID, id));
        }
    }

    @DeleteMapping("/{id}")
    public void getCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }

    private void mapRequestToCategory(CategoryCreateRequest request, Category category) {
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
    }
}
