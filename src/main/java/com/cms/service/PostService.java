package com.cms.service;

import com.cms.dto.DtoMapper;
import com.cms.dto.PostCreateRequest;
import com.cms.dto.PostDto;
import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.entity.Tag;
import com.cms.repository.CategoryRepository;
import com.cms.repository.PostRepository;
import com.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    public Post createPost(PostCreateRequest request) {
        Post post = transform(request);
        post.setCreatedDate(LocalDateTime.now());
        List<Tag> tags = tagRepository.findAllById(request.getTags());
        if (Objects.nonNull(request.getCategory())) {
            Optional<Category> category = categoryRepository.findById(request.getCategory());
            post.setCategory(category.orElse(null));
        }
        post.setTags(new HashSet<>(tags));
        return postRepository.save(post);
    }

    public void updatePost(PostCreateRequest request, int id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post existingPost = optional.get();
            transform(request, existingPost);
            existingPost.setUpdatedDate(LocalDateTime.now());
            postRepository.saveAndFlush(existingPost);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public PostDto getPostById(int id) {
        return postRepository.findById(id)
                .map(DtoMapper.POST_TO_DTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id));
    }

    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public Post getBySlug(String postSlug) {
        return postRepository.getBySlug(postSlug);
    }

    private Post transform(PostCreateRequest request) {
        Post post = new Post();
        transform(request, post);
        return post;
    }

    private void transform(PostCreateRequest request, Post post) {
        post.setDescription(request.getDescription());
        post.setSlug(request.getSlug());
        post.setContent(request.getContent());
        post.setExcerpt(request.getExcerpt());
        post.setTitle(request.getTitle());
    }
}
