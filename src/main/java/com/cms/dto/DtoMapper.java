package com.cms.dto;

import com.cms.constant.ToolType;
import com.cms.dto.request.CategoryCreateRequest;
import com.cms.dto.request.TagCreateRequest;
import com.cms.dto.response.PostResponseDto;
import com.cms.dto.response.TagDto;
import com.cms.dto.response.ToolResponseDto;
import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.entity.Tag;
import com.cms.entity.Tool;
import com.cms.entity.Webpage;

import java.util.Optional;
import java.util.function.Function;

public class DtoMapper {

    public static final Function<Category, CategoryDto> CATEGORY_TO_DTO = category -> {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCount(category.getPosts().size());
        mapWebpageToDto(category, dto);
        return dto;
    };

    public static void mapRequestToCategory(CategoryCreateRequest request, Category category) {
        category.setName(request.getName());
        mapDtoToWebpage(request, category);
    };

    public static final Function<Tag, TagDto> TAG_TO_DTO = tag -> {
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setCount(tag.getPosts().size());
        mapWebpageToDto(tag, dto);
        return dto;
    };

    public static void mapRequestToTag(TagCreateRequest request, Tag tag) {
        tag.setName(request.getName());
        mapDtoToWebpage(request, tag);
    }

    public static final Function<Post, PostResponseDto> POST_TO_DTO = post -> {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setHeading(post.getHeading());
        dto.setUpdated(post.getUpdatedDate());
        dto.setCreated(post.getCreatedDate());
        dto.setCategory(Optional.ofNullable(post.getCategory()).map(CATEGORY_TO_DTO).orElse(null));
        dto.setTags(post.getTags().stream().map(TAG_TO_DTO).toList());
        dto.setContent(post.getContent());
        dto.setExcerpt(post.getExcerpt());
        mapWebpageToDto(post, dto);
        return dto;
    };

    public static final Function<ToolType, ToolTypeDto> TOOL_TYPE_TO_DTO = toolType -> new ToolTypeDto(toolType.name(), toolType.getLabel());

    public static final Function<Tool, ToolResponseDto> TOOL_TO_DTO = tool -> {
        ToolResponseDto dto = new ToolResponseDto();
        dto.setType(TOOL_TYPE_TO_DTO.apply(ToolType.getTypeByName(tool.getType())));
        dto.setContent(tool.getContent());
        dto.setName(tool.getName());
        dto.setId(tool.getId());
        dto.setExcerpt(tool.getExcerpt());
        dto.setTags(tool.getTags().stream().map(TAG_TO_DTO).toList());
        mapWebpageToDto(tool, dto);
        return dto;
    };

    private static void mapWebpageToDto(Webpage webpage, WebpageDto dto) {
        dto.setTitle(webpage.getTitle());
        dto.setSlug(webpage.getSlug());
        dto.setDescription(webpage.getDescription());
    }

    public static void mapDtoToWebpage(WebpageDto dto, Webpage webpage) {
        webpage.setDescription(dto.getDescription());
        webpage.setSlug(dto.getSlug());
        webpage.setTitle(dto.getTitle());
    }
}
