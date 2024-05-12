package com.cms.controller;

import com.cms.dto.DtoMapper;
import com.cms.dto.PostCreateRequest;
import com.cms.dto.PostDto;
import com.cms.entity.Post;
import com.cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping({""})
    private List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("")
    private PostDto create(@RequestBody PostCreateRequest request) {
        Post post = postService.createPost(request);
        return DtoMapper.POST_TO_DTO.apply(post);
    }

    @GetMapping("/{id}")
    private PostDto findById(@PathVariable int id) {
        return postService.getPostById(id);
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
