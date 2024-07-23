package com.cms.dto;

import com.cms.constant.ToolType;
import com.cms.dto.response.CategoryDto;
import com.cms.dto.response.CategorySnippetDto;
import com.cms.dto.response.PostResponseDto;
import com.cms.dto.response.TagDto;
import com.cms.dto.response.ToolResponseDto;
import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.entity.Tag;
import com.cms.entity.Tool;

import java.util.Optional;
import java.util.function.Function;

public class DtoMapper {

    public static final Function<Category, CategoryDto> CATEGORY_TO_DTO = category -> {
        CategoryDto dto = new CategoryDto();
        dto.setCount(category.getPosts().size());
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setTitle(category.getTitle());
        dto.setDescription(category.getDescription());
        return dto;
    };

    public static final Function<Category, CategorySnippetDto> CATEGORY_TO_SNIPPET = category -> new CategorySnippetDto(category.getName(), category.getSlug());

    public static final Function<Tag, TagDto> TAG_TO_DTO = tag -> {
        TagDto dto = new TagDto();
        dto.setCount(tag.getPosts().size());
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setSlug(tag.getSlug());
        dto.setTitle(tag.getTitle());
        dto.setDescription(tag.getDescription());
        return dto;
    };

    public static final Function<Post, PostResponseDto> POST_TO_DTO = post -> {
        PostResponseDto dto = new PostResponseDto();
        dto.setCategory(Optional.ofNullable(post.getCategory()).map(CATEGORY_TO_DTO).orElse(null));
        dto.setId(post.getId());
        dto.setHeading(post.getHeading());
        dto.setUpdated(post.getUpdated());
        dto.setCreated(post.getCreated());
        dto.setTags(post.getTags().stream().map(TAG_TO_DTO).toList());
        dto.setContent(post.getContent());
        dto.setExcerpt(post.getExcerpt());
        dto.setDescription(post.getDescription());
        dto.setSlug(post.getSlug());
        dto.setImage(post.getImage());
        return dto;
    };

    public static final Function<ToolType, ToolTypeDto> TOOL_TYPE_TO_DTO = toolType -> new ToolTypeDto(toolType.name(), toolType.getLabel());

    public static final Function<Tool, ToolResponseDto> TOOL_TO_DTO = tool -> {
        ToolResponseDto dto = new ToolResponseDto();
        dto.setType(TOOL_TYPE_TO_DTO.apply(ToolType.getTypeByName(tool.getType())));
        dto.setContent(tool.getContent());
        dto.setTagline(tool.getTagline());
        dto.setTags(tool.getTags().stream().map(TAG_TO_DTO).toList());
        dto.setName(tool.getName());
        dto.setId(tool.getId());
        dto.setSlug(tool.getSlug());
        dto.setTitle(tool.getTitle());
        dto.setDescription(tool.getDescription());
        return dto;
    };

}
