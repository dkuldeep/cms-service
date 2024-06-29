package com.cms.service;

import com.cms.constant.ErrorMessage;
import com.cms.dto.PostCreateRequest;
import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.entity.Tag;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import com.cms.repository.PostRepository;
import com.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id));
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(int id) {
        return postRepository.findById(id);
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
