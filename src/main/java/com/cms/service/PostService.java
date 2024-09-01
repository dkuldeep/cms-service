package com.cms.service;

import com.cms.business.PostAdapter;
import com.cms.business.PostRef;
import com.cms.constant.ErrorMessage;
import com.cms.constant.PostType;
import com.cms.dto.request.PostCreateRequest;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Post;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.PostRepository;
import com.cms.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private WordpressService wordpressService;

    @Autowired
    private ImageService imageService;

    public Post createPost(PostCreateRequest request) {
        Post post = new Post();
        mapRequestToPost(request, post);
        post.setCreated(LocalDateTime.now());
        return postRepository.saveAndFlush(post);
    }

    public Post createPost(PostRef postRef) {
        Post post = new Post();
        post.setHeading(postRef.getHeading());
        post.setTags(new HashSet<>(postRef.getTags()));
        post.setType(postRef.getType().name());
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

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public Optional<Post> findBySlug(String slug) {
        Post search = new Post();
        search.setSlug(slug);
        return postRepository.findOne(Example.of(search));
    }

    private void mapRequestToPost(PostCreateRequest request, Post post) {
        post.setHeading(request.getHeading());
        post.setExcerpt(request.getExcerpt());
        post.setContent(request.getContent());
        post.setType(request.getType().name());
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
                posts.add(createPost(new PostAdapter(wordpressPost, imageService, tagRepository)));
            }
        }
        posts = postRepository.saveAllAndFlush(posts);
        return posts.size();
    }

    public List<Post> findByType(PostType postType, int count) {
        Post search = new Post();
        search.setType(postType.name());
        return postRepository.findAll(Example.of(search), Pageable.ofSize(count)).stream().toList();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public String uploadImage(Integer id, MultipartFile file) throws IOException {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            String path = imageService.saveImage(file);
            post.setImage(path);
            return path;
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id));
        }
    }

    @Transactional
    public void removeImage(Integer id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            post.setImage(null);
        }
    }
}
