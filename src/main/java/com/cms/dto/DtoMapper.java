package com.cms.dto;

import com.cms.constant.PostType;
import com.cms.constant.ToolType;
import com.cms.dto.response.PostResponse;
import com.cms.dto.response.PostResponseSnippet;
import com.cms.dto.response.TagResponse;
import com.cms.dto.response.TagResponseSnippet;
import com.cms.dto.response.ToolResponse;
import com.cms.dto.response.ToolResponseSnippet;
import com.cms.entity.Post;
import com.cms.entity.Tag;
import com.cms.entity.Tool;
import com.cms.entity.Webpage;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class DtoMapper {
    private DtoMapper() {
    }

    public static final Function<ToolType, TypeDto> TOOL_TYPE_TO_DTO = toolType -> new TypeDto(toolType.name(), toolType.getLabel());

    public static final Function<PostType, TypeDto> POST_TYPE_TO_DTO = postType -> new TypeDto(postType.name(), postType.getLabel());

    public static final Function<Tag, TagResponseSnippet> TAG_TO_SNIPPET = tag -> {
        TagResponseSnippet snippet = new TagResponseSnippet();
        snippet.setId(tag.getId());
        snippet.setName(tag.getName());
        snippet.setSlug(tag.getSlug());
        snippet.setTitle(tag.getTitle());
        snippet.setDescription(tag.getDescription());
        snippet.setCount(tag.getPosts().size());
        return snippet;
    };

    public static final Function<Post, PostResponseSnippet> POST_TO_SNIPPET = post -> {
        PostResponseSnippet dto = new PostResponseSnippet();
        dto.setImage(post.getImage());
        dto.setHeading(post.getHeading());
        dto.setSlug(post.getSlug());
        dto.setExcerpt(post.getExcerpt());
        dto.setCreated(post.getCreated());
        dto.setTags(post.getTags().stream().map(TAG_TO_SNIPPET).toList());
        dto.setType(POST_TYPE_TO_DTO.apply(Optional.ofNullable(post.getType()).orElse(PostType.UNCATEGORIZED)));
        dto.setId(post.getId());
        dto.setDescription(post.getDescription());
        return dto;
    };

    public static final Function<Tag, TagResponse> TAG_TO_DTO = tag -> {
        TagResponse dto = new TagResponse(TAG_TO_SNIPPET.apply(tag));
        dto.setRelatedPosts(tag.getPosts()
                .stream()
                .sorted(Comparator.comparing(Webpage::getCreated).reversed())
                .map(POST_TO_SNIPPET)
                .toList());
        dto.setRelatedTools(tag.getTools().stream().map(Webpage::getSlug).toList());
        return dto;
    };

    public static final Function<Post, PostResponse> POST_TO_DTO = post -> {
        PostResponse dto = new PostResponse(POST_TO_SNIPPET.apply(post));
        dto.setContent(post.getContent());
        dto.setUpdated(post.getUpdated());
        dto.setRelated(post.getTags().stream().flatMap(tag -> tag.getPosts().stream()).distinct().map(POST_TO_SNIPPET).toList());
        return dto;
    };

    public static final Function<Tool, ToolResponseSnippet> TOOL_TO_SNIPPET = tool -> {
        ToolResponseSnippet snippet = new ToolResponseSnippet();
        snippet.setId(tool.getId());
        snippet.setSlug(tool.getSlug());
        snippet.setTitle(tool.getTitle());
        snippet.setDescription(tool.getDescription());
        snippet.setName(tool.getName());
        snippet.setType(TOOL_TYPE_TO_DTO.apply(tool.getType()));
        snippet.setTagline(tool.getTagline());
        snippet.setTags(tool.getTags().stream().map(TAG_TO_SNIPPET).toList());
        return snippet;
    };

    public static final Function<Tool, ToolResponse> TOOL_TO_DTO = tool -> {
        ToolResponse dto = new ToolResponse(TOOL_TO_SNIPPET.apply(tool));
        dto.setContent(tool.getContent());
        dto.setRelatedBlogs(tool.getTags().stream().flatMap(tag -> tag.getPosts().stream()).map(POST_TO_SNIPPET).toList());
        return dto;
    };

}
