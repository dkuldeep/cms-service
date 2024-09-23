package com.cms.dto;

import com.cms.constant.PostType;
import com.cms.constant.ToolType;
import com.cms.dto.response.PostResponseDto;
import com.cms.dto.response.PostSnippetDto;
import com.cms.dto.response.TagDto;
import com.cms.dto.response.ToolResponseDto;
import com.cms.entity.Post;
import com.cms.entity.Tag;
import com.cms.entity.Tool;
import com.cms.entity.Webpage;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class DtoMapper {

    public static final Function<ToolType, TypeDto> TOOL_TYPE_TO_DTO = toolType -> new TypeDto(toolType.name(), toolType.getLabel());
    public static final Function<PostType, TypeDto> POST_TYPE_TO_DTO = postType -> new TypeDto(postType.name(), postType.getLabel());

    public static final Function<Post, PostSnippetDto> POST_TO_SNIPPET_DTO = post -> {
        PostSnippetDto dto = new PostSnippetDto();
        dto.setExcerpt(post.getExcerpt());
        dto.setImage(post.getImage());
        dto.setSlug(post.getSlug());
        dto.setHeading(post.getHeading());
        dto.setCreated(post.getCreated());
        return dto;
    };

    public static final Function<Tag, TagDto> TAG_TO_DTO = tag -> {
        TagDto dto = new TagDto();
        dto.setCount(tag.getPosts().size());
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setSlug(tag.getSlug());
        dto.setTitle(tag.getTitle());
        dto.setDescription(tag.getDescription());
        dto.setRelatedPosts(tag.getPosts()
                .stream()
                .sorted(Comparator.comparing(Webpage::getCreated).reversed())
                .map(POST_TO_SNIPPET_DTO)
                .toList());
        return dto;
    };

    public static final Function<Post, PostResponseDto> POST_TO_DTO = post -> {
        PostResponseDto dto = new PostResponseDto();
        dto.setType(POST_TYPE_TO_DTO.apply(Optional.ofNullable(post.getType()).orElse(PostType.UNCATEGORIZED)));
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

    public static final Function<Tool, ToolResponseDto> TOOL_TO_DTO = tool -> {
        ToolResponseDto dto = new ToolResponseDto();
        dto.setType(TOOL_TYPE_TO_DTO.apply(tool.getType()));
        dto.setContent(tool.getContent());
        dto.setTagline(tool.getTagline());
        dto.setTags(tool.getTags().stream().map(TAG_TO_DTO).toList());
        dto.setName(tool.getName());
        dto.setId(tool.getId());
        dto.setSlug(tool.getSlug());
        dto.setTitle(tool.getTitle());
        dto.setDescription(tool.getDescription());
        dto.setRelatedBlogs(tool.getTags().stream().flatMap(tag -> tag.getPosts().stream()).map(POST_TO_SNIPPET_DTO).toList());
        return dto;
    };

}
