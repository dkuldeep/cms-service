package com.cms.service;

import com.cms.business.BlogRef;
import com.cms.business.WordpressBlogImpl;
import com.cms.constant.ErrorMessage;
import com.cms.dto.request.BlogCreateRequest;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Blog;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.BlogRepository;
import com.cms.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class BlogService {

    private static final Logger log = LoggerFactory.getLogger(BlogService.class);
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private WordpressService wordpressService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TagRepository tagRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public int importFromWordpress(String type, String value) throws MalformedURLException, URISyntaxException {
        List<WordpressPost> wordpressPosts = wordpressService.fetchPosts(value, type);
        List<Blog> blogs = new ArrayList<>(0);
        for (WordpressPost wordpressPost : wordpressPosts) {
            Blog search = new Blog();
            search.setSlug(wordpressPost.getSlug());
            if (blogRepository.exists(Example.of(search))) {
                log.warn(MessageFormat.format(ErrorMessage.OBJECT_ALREADY_EXIST_WITH_SLUG, "Blog", wordpressPost.getSlug()));
            } else {
                Blog blog = createBlog(new WordpressBlogImpl(wordpressPost, imageService, tagRepository));
                blogs.add(blog);
            }
        }
        blogRepository.saveAllAndFlush(blogs);
        return blogs.size();
    }

    public Blog createBlog(BlogRef blogRef) {
        Blog blog = new Blog();
        blog.setContent(blogRef.getContent());
        blog.setHeading(blogRef.getHeading());
        blog.setExcerpt(blogRef.getExcerpt());
        blog.setTags(new HashSet<>(blogRef.getTags()));
        blog.setCreated(blogRef.getCreated());
        blog.setUpdated(blogRef.getUpdated());
        blog.setDescription(blogRef.getDescription());
        blog.setSlug(blogRef.getSlug());
        return blog;
    }

    public void deleteById(int id) {
        blogRepository.deleteById(id);
    }

    public Optional<Blog> findById(int id) {
        return blogRepository.findById(id);
    }

    public Blog create(BlogCreateRequest request) {
        Blog blog = new Blog();
        blog.setCreated(LocalDateTime.now());
        mapRequestToBlog(request, blog);
        return blogRepository.saveAndFlush(blog);
    }

    @Transactional
    public void update(BlogCreateRequest request, int id) {
        Optional<Blog> optional = blogRepository.findById(id);
        if (optional.isPresent()) {
            Blog blog = optional.get();
            blog.setUpdated(LocalDateTime.now());
            mapRequestToBlog(request, blog);
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id));
        }
    }

    private void mapRequestToBlog(BlogCreateRequest request, Blog blog) {
        blog.setHeading(request.getHeading());
        blog.setExcerpt(request.getExcerpt());
        blog.setContent(request.getContent());
        blog.setTags(request.getTags().stream()
                .filter(Objects::nonNull)
                .map(integer -> tagRepository.getReferenceById(integer))
                .collect(Collectors.toSet()));
        blog.setSlug(request.getSlug());
        blog.setDescription(request.getDescription());
    }

    @Transactional
    public String uploadImage(Integer id, MultipartFile file) throws IOException {
        Optional<Blog> optional = blogRepository.findById(id);
        if (optional.isPresent()) {
            Blog blog = optional.get();
            String path = imageService.saveImage(file);
            blog.setImage(path);
            return path;
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id));
        }
    }

    public List<Blog> getBySlug(String slug) {
        Blog blog = new Blog();
        blog.setSlug(slug);
        return blogRepository.findAll(Example.of(blog));
    }
}
