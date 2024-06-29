package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.PostCreateRequest;
import com.cms.dto.PostDto;
import com.cms.entity.Post;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    private List<PostDto> getAllPosts(@RequestParam(required = false) String slug) {
        List<Post> posts;
        if (Objects.nonNull(slug)) {
            Post post = postService.getBySlug(slug);
            if (Objects.isNull(post)) {
                throw new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_SLUG_NOT_FOUND, slug));
            }
            posts = Collections.singletonList(post);
        } else {
            posts = postService.getAllPosts();
        }
        return posts.stream().map(DtoMapper.POST_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping("")
    private PostDto create(@RequestBody PostCreateRequest request) {
        Post post = postService.createPost(request);
        return DtoMapper.POST_TO_DTO.apply(post);
    }

    @GetMapping("/{id}")
    private PostDto findById(@PathVariable int id) {
        Optional<Post> optionalPost = postService.getPostById(id);
        return optionalPost.map(DtoMapper.POST_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id)));
    }

    @PutMapping("/{id}")
    private void updateById(@RequestBody PostCreateRequest request,
                            @PathVariable("id") int id) {
        postService.updatePost(request, id);
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable("id") int id) {
        postService.deleteById(id);
    }
}
