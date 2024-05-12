package com.cms.dto;

import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.entity.Tag;

import java.util.Objects;
import java.util.function.Function;

public class DtoMapper {

    public static final Function<Category, CategoryDto> CATEGORY_TO_DTO = category -> {
        CategoryDto dto = new CategoryDto();
        if (Objects.nonNull(category)) {
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            dto.setSlug(category.getSlug());
            dto.setId(category.getId());
            dto.setCount(category.getPosts().size());
        }
        return dto;
    };

    public static final Function<CategoryDto, Category> DTO_TO_CATEGORY = dto -> {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setSlug(dto.getSlug());
        category.setId(dto.getId());
        return category;
    };

    public static final Function<Tag, TagDto> TAG_TO_DTO = tag -> {
        TagDto dto = new TagDto();
        if (Objects.nonNull(tag)) {
            dto.setName(tag.getName());
            dto.setDescription(tag.getDescription());
            dto.setSlug(tag.getSlug());
            dto.setId(tag.getId());
            dto.setCount(tag.getPosts().size());
        }
        return dto;
    };

    public static final Function<TagDto, Tag> DTO_TO_TAG = dto -> {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setDescription(dto.getDescription());
        tag.setSlug(dto.getSlug());
        tag.setId(dto.getId());
        return tag;
    };

    public static final Function<Post, PostDto> POST_TO_DTO = post -> {
        PostDto dto = new PostDto();
        dto.setSlug(post.getSlug());
        dto.setId(post.getId());
        dto.setDescription(post.getDescription());
        dto.setUpdated(post.getUpdatedDate());
        dto.setCreated(post.getCreatedDate());
        dto.setCategory(CATEGORY_TO_DTO.apply(post.getCategory()));
        dto.setTags(post.getTags().stream().map(TAG_TO_DTO).toList());
        dto.setContent(post.getContent());
        dto.setExcerpt(post.getExcerpt());
        dto.setTitle(post.getTitle());
        return dto;
    };

}
