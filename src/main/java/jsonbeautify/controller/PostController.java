package jsonbeautify.controller;

import jsonbeautify.PostType;
import jsonbeautify.dto.PostContentDto;
import jsonbeautify.dto.PostDto;
import jsonbeautify.entity.Post;
import jsonbeautify.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("")
  private List<PostDto> findAll(@RequestParam(required = false) String type, @RequestParam(required = false) String topic, @RequestParam(required = false) String tag) {
    List<Post> posts = new ArrayList<>();
    if (topic != null) {
      List<Post> posts1 = postService.getPostsByTopic(topic);
      return posts1.stream().map(this::transform).collect(Collectors.toList());
    }
    if (tag != null) {
      List<Post> posts1 = postService.getPostsByTag(tag);
      return posts1.stream().map(this::transform).collect(Collectors.toList());
    }
    if (type == null || type.trim().equals("")) {
      posts = postService.findAll();
    } else if (PostType.PAGE.name().equalsIgnoreCase(type)
        || PostType.POST.name().equalsIgnoreCase(type)
        || PostType.FORMATTER.name().equalsIgnoreCase(type)) {
      posts = postService.getPostsByType(type);
    } else if (PostType.MISC.name().equalsIgnoreCase(type) || type.equalsIgnoreCase("misc")) {
      posts = postService.getPostsByTypeUnknown();
    }
    return posts.stream().map(this::transform).collect(Collectors.toList());
  }

  @PostMapping("")
  private int create(@RequestBody PostDto dto) {
    Post post = new Post();
    post.setCreated(LocalDateTime.now());
    transform(dto, post);
    post = postService.saveOrUpdate(post);
    return post == null ? -1 : post.getId();
  }

  @GetMapping("/{id}")
  private PostDto findById(@PathVariable int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      return transform(optional.get());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @PutMapping("/{id}")
  private PostDto updateById(@RequestBody PostDto dto, @PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      Post existingPost = optional.get();
      existingPost.setModified(LocalDateTime.now());
      transform(dto, existingPost);
      return transform(postService.saveOrUpdate(existingPost));
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @DeleteMapping("/{id}")
  private void deleteById(@PathVariable("id") int id) {
    postService.deleteById(id);
  }

  @GetMapping("/{id}/content")
  private PostContentDto getContentById(@PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      return new PostContentDto(optional.get());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @PutMapping("/{id}/content")
  private PostDto updateContentById(@RequestBody PostContentDto dto, @PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      Post existingPost = optional.get();
      existingPost.setContent(dto.getContent());
      existingPost.setModified(LocalDateTime.now());
      return transform(postService.saveOrUpdate(existingPost));
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @GetMapping("getPostBySlug")
  private PostDto getPostBySlug(@RequestParam String slug) {
    Optional<Post> optional = postService.getBySlug(slug);
    if (optional.isPresent()) {
      return transform(optional.get());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with slug: " + slug);
    }
  }

  @GetMapping("getContentBySlug")
  private PostContentDto getContentBySlug(@RequestParam String slug) {
    Optional<Post> optional = postService.getBySlug(slug);
    if (optional.isPresent()) {
      return new PostContentDto(optional.get());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with slug: " + slug);
    }
  }

  private PostDto transform(Post post) {
    PostDto dto = new PostDto();
    dto.setActive(post.isActive());
    dto.setId(post.getId());
    dto.setType(post.getType());
    dto.setDescription(post.getDescription());
    dto.setKeywords(post.getKeywords());
    dto.setSlug(post.getSlug());
    dto.setTitle(post.getTitle());
    dto.setModified(post.getModified());
    dto.setCreated(post.getCreated());
    dto.setTopic(post.getTopic());
    dto.setTags(Arrays.asList(post.getTags().split(",")));
    if ("POST".equalsIgnoreCase(post.getType())) {
      dto.setPath("/" + post.getTopic() + "/" + post.getSlug());
    }
    return dto;
  }

  private void transform(PostDto source, Post target) {
    target.setActive(source.isActive());
    target.setType(source.getType());
    target.setDescription(source.getDescription());
    target.setKeywords(source.getKeywords());
    target.setSlug(source.getSlug());
    target.setTitle(source.getTitle());
    target.setTags(String.join(",", source.getTags()));
    target.setTopic(source.getTopic());
  }
}
