package com.cms.service;

import com.cms.constant.ErrorMessage;
import com.cms.dto.PostCreateRequest;
import com.cms.entity.Post;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import com.cms.repository.PostRepository;
import com.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    public Post createPost(PostCreateRequest request) {
        Post post = new Post();
        transform(request, post);
        post.setCreatedDate(LocalDateTime.now());
        return postRepository.saveAndFlush(post);
    }

    @Transactional
    public void updatePost(PostCreateRequest request, int id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post existingPost = optional.get();
            transform(request, existingPost);
            existingPost.setUpdatedDate(LocalDateTime.now());
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

    private void transform(PostCreateRequest request, Post post) {
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setExcerpt(request.getExcerpt());
        post.setDescription(request.getDescription());
        post.setCategory(Optional.ofNullable(request.getCategory())
                .map(integer -> categoryRepository.getReferenceById(integer))
                .orElse(null));
        post.setTags(request.getTags().stream()
                .filter(Objects::nonNull)
                .map(integer -> tagRepository.getReferenceById(integer))
                .collect(Collectors.toSet()));
        post.setContent(request.getContent());
    }
}
