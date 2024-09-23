package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.constant.PostType;
import com.cms.dto.DtoMapper;
import com.cms.dto.ImageDto;
import com.cms.dto.TypeDto;
import com.cms.dto.request.PostCreateRequest;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.response.PostResponseDto;
import com.cms.entity.Post;
import com.cms.entity.Webpage;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.HasSlug;
import com.cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cms.constant.ErrorMessage.POST_CREATED;

@RestController
@RequestMapping("posts")
public class PostController implements HasSlug {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostResponseDto> getAllPosts(@RequestParam(required = false) PostType type) {
        if (type == null) {
            return postService.findAll().stream().map(DtoMapper.POST_TO_DTO).toList();
        }
        return postService.findByType(type, 100).stream().map(DtoMapper.POST_TO_DTO).toList();
    }

    @Override
    @GetMapping("/slug/{slug}")
    public Object getBySlug(@PathVariable String slug) {
        Optional<Post> optional = postService.findBySlug(slug);
        return optional.map(DtoMapper.POST_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format(ErrorMessage.POST_NOT_FOUND_WITH_SLUG, slug)));
    }

    @GetMapping("latest3")
    public List<PostResponseDto> latest3() {
        return postService.findByType(PostType.BLOG, 3)
                .stream()
                .sorted(Comparator.comparing(Webpage::getCreated).reversed())
                .map(DtoMapper.POST_TO_DTO)
                .toList();
    }

    @GetMapping("{id}")
    public PostResponseDto findById(@PathVariable int id) {
        Optional<Post> optionalPost = postService.findById(id);
        return optionalPost.map(DtoMapper.POST_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id)));
    }

    @PostMapping
    public ObjectCreated create(@RequestBody PostCreateRequest request) {
        Post post = postService.createPost(request);
        return new ObjectCreated(post.getId(), POST_CREATED);
    }

    @PutMapping("{id}")
    public ObjectUpdated updateById(@RequestBody PostCreateRequest request,
                                    @PathVariable("id") int id) {
        postService.updatePost(request, id);
        return new ObjectUpdated(ErrorMessage.POST_UPDATED);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") int id) {
        postService.deleteById(id);
    }

    @PatchMapping("{id}")
    public ImageDto uploadImage(@PathVariable("id") Integer id,
                                @RequestParam("file") MultipartFile file) throws IOException {
        String path = postService.uploadImage(id, file);
        return new ImageDto(path);
    }

    @DeleteMapping("{id}/image")
    public void removeImage(@PathVariable("id") Integer id) throws IOException {
        postService.removeImage(id);
    }

    @GetMapping("types")
    public List<TypeDto> getTypes() {
        return Arrays.stream(PostType.values())
                .map(postType -> new TypeDto(postType.name(), postType.getLabel()))
                .collect(Collectors.toList());
    }
}
