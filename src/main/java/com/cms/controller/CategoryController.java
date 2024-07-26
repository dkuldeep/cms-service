package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.constant.DefaultCategory;
import com.cms.dto.DtoMapper;
import com.cms.dto.request.CategoryCreateRequest;
import com.cms.dto.response.CategoryDto;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.response.PostResponseDto;
import com.cms.dto.response.PostSnippetDto;
import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import com.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(required = false) String slug) {
        List<Category> categories = new ArrayList<>(0);
        if (Objects.nonNull(slug)) {
            Optional<Category> optionalCategory = categoryService.findBySlug(slug);
            if (optionalCategory.isPresent()) {
                categories = Collections.singletonList(optionalCategory.get());
                return categories.stream().map(DtoMapper.CATEGORY_TO_DTO).toList();
            }
        } else {
            categories = categoryRepository.findAll();
        }
        return categories.stream()
                .filter(category -> !DefaultCategory.getAllSlugs().contains(category.getSlug()))
                .map(DtoMapper.CATEGORY_TO_DTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        return optional
                .map(DtoMapper.CATEGORY_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_ID, id)));
    }

    @GetMapping("/{id}/posts")
    public List<PostSnippetDto> getPostsByCategory(@PathVariable Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            Set<Post> posts = category.getPosts();
            return posts.stream().map(DtoMapper.POST_TO_SNIPPET_DTO).toList();
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_ID, id));
        }
    }

    @GetMapping("/{id}/postBySlug")
    public PostResponseDto getPostByCategoryAndSlug(@PathVariable Integer id,
                                                    @RequestParam String slug) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Set<Post> allPosts = optionalCategory.get().getPosts();
            Optional<Post> optionalPost = allPosts.stream().filter(post1 -> post1.getSlug().equals(slug)).findFirst();
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                List<Post> related = allPosts.stream().filter(post1 -> !post1.getSlug().equals(slug)).toList();
                PostResponseDto dto = DtoMapper.POST_TO_DTO.apply(post);
                dto.setRelated(related.stream().map(DtoMapper.POST_TO_SNIPPET_DTO).toList());
                return dto;
            } else {
                throw new ObjectNotFoundException(String.format(ErrorMessage.POST_NOT_FOUND_WITH_SLUG, slug));
            }
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_SLUG, slug));
        }
    }

    @GetMapping("/pageBySlug")
    public PostResponseDto pageBySlug(@RequestParam String slug) {
        Optional<Category> optionalCategory = categoryService.findBySlug(DefaultCategory.PAGES.getSlug());
        if (optionalCategory.isPresent()) {
            Set<Post> allPosts = optionalCategory.get().getPosts();
            Optional<Post> optionalPost = allPosts.stream().filter(post1 -> post1.getSlug().equals(slug)).findFirst();
            if (optionalPost.isPresent()) {
                return DtoMapper.POST_TO_DTO.apply(optionalPost.get());
            } else {
                throw new ObjectNotFoundException(String.format(ErrorMessage.POST_NOT_FOUND_WITH_SLUG, slug));
            }
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_SLUG, DefaultCategory.PAGES.getSlug()));
        }
    }

    @GetMapping("/blogBySlug")
    public PostResponseDto blogBySlug(@RequestParam String slug) {
        Optional<Category> optionalCategory = categoryService.findBySlug(DefaultCategory.BLOG.getSlug());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            return getPostByCategoryAndSlug(category.getId(), slug);
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_WITH_SLUG, DefaultCategory.BLOG.getSlug()));
        }
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
