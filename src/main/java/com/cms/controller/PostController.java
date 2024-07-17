package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.request.PostCreateRequest;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.PostResponseDto;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.wordpress.WordpressImportRequest;
import com.cms.entity.Post;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cms.constant.ErrorMessage.POST_CREATED;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostResponseDto> getAllPosts(@RequestParam(required = false) String slug,
                                             @RequestParam(required = false) Integer category) {
        List<Post> posts;
        if (Objects.nonNull(category) && Objects.nonNull(slug)) {
            Set<Post> set = postService.getPostsByCategory(category);
            posts = set.stream().filter(post -> post.getSlug().equals(slug)).collect(Collectors.toList());
            return posts.stream().map(DtoMapper.POST_TO_DTO).collect(Collectors.toList());
        } else {
            if (Objects.nonNull(category)) {
                posts = postService.getPostsByCategory(category).stream().toList();
                return posts.stream().map(DtoMapper.POST_TO_DTO).collect(Collectors.toList());
            }
            if (Objects.nonNull(slug)) {
                Post post = postService.getBySlug(slug);
                if (Objects.isNull(post)) {
                    throw new ObjectNotFoundException(String.format(ErrorMessage.POST_NOT_FOUND_WITH_SLUG, slug));
                }
                posts = Collections.singletonList(post);
                return posts.stream().map(DtoMapper.POST_TO_DTO).collect(Collectors.toList());
            }
            posts = postService.getAllPosts();
            return posts.stream().map(DtoMapper.POST_TO_DTO).collect(Collectors.toList());
        }
    }

    @GetMapping("/byCategoryAndSlug")
    public PostResponseDto getPostByCategoryAndSlug(@RequestParam(required = false) String slug,
                                                    @RequestParam(required = false) String category) {
        Post post = postService.getPostByCategoryAndSlug(category, slug);
        return DtoMapper.POST_TO_DTO.apply(post);
    }

    @GetMapping("latest5")
    public List<PostResponseDto> latest5() {
        List<Post> posts = postService.latest5();
        return posts.stream().map(DtoMapper.POST_TO_DTO).toList();
    }

    @GetMapping("/{id}")
    public PostResponseDto findById(@PathVariable int id) {
        Optional<Post> optionalPost = postService.getPostById(id);
        return optionalPost.map(DtoMapper.POST_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id)));
    }

    @PostMapping
    public ObjectCreated create(@RequestBody PostCreateRequest request) {
        Post post = postService.createPost(request);
        return new ObjectCreated(post.getId(), POST_CREATED);
    }

    @PutMapping("/{id}")
    public ObjectUpdated updateById(@RequestBody PostCreateRequest request,
                                     @PathVariable("id") int id) {
        postService.updatePost(request, id);
        return new ObjectUpdated(ErrorMessage.POST_UPDATED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        postService.deleteById(id);
    }

    @PostMapping("wordpress")
    public ObjectCreated importFromWordpress(@RequestBody WordpressImportRequest request) throws MalformedURLException, URISyntaxException {
        Post post = postService.importFromWordpress(request.getType(), request.getValue());
        return new ObjectCreated(post.getId(), POST_CREATED);
    }
}
