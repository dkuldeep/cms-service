package com.cms.service;

import com.cms.business.PostRef;
import com.cms.business.WordpressPostImpl;
import com.cms.constant.ErrorMessage;
import com.cms.dto.request.PostCreateRequest;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Category;
import com.cms.entity.Post;
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
import java.util.ArrayList;
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
        post.setCreated(LocalDateTime.now());
        mapRequestToPost(request, post);
        return postRepository.saveAndFlush(post);
    }

    public Post createPost(PostRef postRef) {
        Post post = new Post();
        post.setHeading(postRef.getHeading());
        post.setTags(new HashSet<>(postRef.getTags()));
        post.setCategory(postRef.getCategory());
        post.setCreated(postRef.getCreated());
        post.setUpdated(postRef.getUpdated());
        post.setContent(postRef.getContent());
        post.setExcerpt(postRef.getExcerpt());
        post.setDescription(postRef.getDescription());
        post.setSlug(postRef.getSlug());
        return post;
    }

    @Transactional
    public void updatePost(PostCreateRequest request, int id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post existingPost = optional.get();
            mapRequestToPost(request, existingPost);
            existingPost.setUpdated(LocalDateTime.now());
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
        post.setSlug(request.getSlug());
        post.setDescription(request.getDescription());
    }

    public int importFromWordpress(String type, String value) throws MalformedURLException, URISyntaxException {
        List<WordpressPost> wordpressPosts = wordpressService.fetchPosts(value, type);
        List<Post> posts = new ArrayList<>(wordpressPosts.size());
        for (WordpressPost wordpressPost : wordpressPosts) {
            Post search = new Post();
            search.setSlug(wordpressPost.getSlug());
            if (postRepository.exists(Example.of(search))) {
                log.warn(MessageFormat.format(ErrorMessage.OBJECT_ALREADY_EXIST_WITH_SLUG, "Post", wordpressPost.getSlug()));
            } else {
                posts.add(createPost(new WordpressPostImpl(wordpressPost, imageService, tagRepository, categoryRepository)));
            }
        }
        posts = postRepository.saveAllAndFlush(posts);
        return posts.size();
    }

    public Set<Post> getPostsByCategory(Integer categoryId) {
        Optional<Category> optional = categoryRepository.findById(categoryId);
        if (optional.isPresent()) {
            Category category = optional.get();
            return category.getPosts();
        }
        return new HashSet<>(0);
    }

    public List<Post> latest5() {
        Page<Post> page = postRepository.findAll(Pageable.ofSize(5));
        return page.get().toList();
    }
}
