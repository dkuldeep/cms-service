package com.cms.dto;

import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.entity.Tag;

import java.util.function.Function;

public class DtoMapper {

    public static final Function<Category, CategoryDto> CATEGORY_TO_DTO = category -> {
        CategoryDto dto = new CategoryDto();
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setSlug(category.getSlug());
        dto.setTitle(category.getTitle());
        return dto;
    };

    public static final Function<CategoryDto, Category> DTO_TO_CATEGORY = dto -> {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setSlug(dto.getSlug());
        category.setTitle(dto.getTitle());
        return category;
    };

    public static final Function<Tag, TagDto> TAG_TO_DTO = tag -> {
        TagDto dto = new TagDto();
        dto.setName(tag.getName());
        dto.setDescription(tag.getDescription());
        dto.setSlug(tag.getSlug());
        dto.setTitle(tag.getTitle());
        return dto;
    };

    public static final Function<TagDto, Tag> DTO_TO_TAG = dto -> {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setDescription(dto.getDescription());
        tag.setSlug(dto.getSlug());
        tag.setTitle(dto.getTitle());
        return tag;
    };

    public static final Function<Post, PostDto> POST_TO_DTO = post -> {
        PostDto dto = new PostDto();
        dto.setSlug(post.getSlug());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setUpdated(post.getUpdatedDate());
        dto.setCreated(post.getCreatedDate());
        dto.setCategory(CATEGORY_TO_DTO.apply(post.getCategory()));
        dto.setTags(post.getTags().stream().map(TAG_TO_DTO).toList());
        dto.setContent(post.getContent());
        dto.setExcerpt(post.getExcerpt());
        return dto;
    };

}
