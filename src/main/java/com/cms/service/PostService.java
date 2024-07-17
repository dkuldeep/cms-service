package com.cms.service;

import com.cms.business.PostRef;
import com.cms.business.WordpressPostImpl;
import com.cms.constant.ErrorMessage;
import com.cms.dto.request.PostCreateRequest;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Category;
import com.cms.entity.Post;
import com.cms.exception.ObjectAlreadyExistException;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.CategoryRepository;
import com.cms.repository.PostRepository;
import com.cms.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private WordpressService wordpressService;

    @Autowired
    private ImageService imageService;

    public Post createPost(PostCreateRequest request) {
        Post post = new Post();
        mapRequestToPost(request, post);
        post.setCreatedDate(LocalDateTime.now());
        return postRepository.saveAndFlush(post);
    }

    public Post createPost(PostRef postRef) {
        Post post = new Post();
        post.setHeading(postRef.getHeading());
        post.setTags(new HashSet<>(postRef.getTags()));
        post.setCategory(postRef.getCategory());
        post.setCreatedDate(postRef.getCreatedDate());
        post.setUpdatedDate(postRef.getUpdatedDate());
        post.setContent(postRef.getContent());
        post.setExcerpt(postRef.getExcerpt());
        post.setDescription(postRef.getDescription());
        post.setTitle(postRef.getTitle());
        post.setSlug(postRef.getSlug());
        return postRepository.saveAndFlush(post);
    }

    @Transactional
    public void updatePost(PostCreateRequest request, int id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post existingPost = optional.get();
            mapRequestToPost(request, existingPost);
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

    private void mapRequestToPost(PostCreateRequest request, Post post) {
        post.setHeading(request.getHeading());
        post.setExcerpt(request.getExcerpt());
        post.setContent(request.getContent());
        post.setCategory(Optional.ofNullable(request.getCategory())
                .map(integer -> categoryRepository.getReferenceById(integer))
                .orElse(null));
        post.setTags(request.getTags().stream()
                .filter(Objects::nonNull)
                .map(integer -> tagRepository.getReferenceById(integer))
                .collect(Collectors.toSet()));
        //
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setDescription(request.getDescription());
    }

    public Post importFromWordpress(String type, String value) throws MalformedURLException, URISyntaxException {
        List<WordpressPost> wordpressPosts = wordpressService.fetchPosts(value, type);
        for (WordpressPost wordpressPost : wordpressPosts) {
            Post search = new Post();
            search.setSlug(wordpressPost.getSlug());
            if (postRepository.exists(Example.of(search))) {
                String msg = MessageFormat.format(ErrorMessage.OBJECT_ALREADY_EXIST_WITH_SLUG, "Post", wordpressPost.getSlug());
                log.warn(msg);
                throw new ObjectAlreadyExistException(msg);
            } else {
                return createPost(new WordpressPostImpl(wordpressPost, imageService, tagRepository, categoryRepository));
            }
        }
        return null;
    }

    public Set<Post> getPostsByCategory(Integer categoryId) {
        Optional<Category> optional = categoryRepository.findById(categoryId);
        if (optional.isPresent()) {
            Category category = optional.get();
            return category.getPosts();
        }
        return new HashSet<>(0);
    }

    public Post getPostByCategoryAndSlug(String category, String slug) {
        Category search = new Category();
        search.setSlug(category);
        Optional<Category> optional = categoryRepository.findOne(Example.of(search));
        if (optional.isPresent()) {
            Category category1 = optional.get();
            Optional<Post> optionalPost = category1.getPosts().stream().filter(post -> post.getSlug().equals(slug)).findFirst();
            return optionalPost.orElseThrow();
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.POST_NOT_FOUND_WITH_SLUG, slug));
        }
    }

    public List<Post> latest5() {
        Page<Post> page = postRepository.findAll(Pageable.ofSize(5));
        return page.get().toList();
    }
}
