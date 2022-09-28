package jsonbeautify.controller;

import jsonbeautify.PostType;
import jsonbeautify.dto.PostContentDto;
import jsonbeautify.dto.PostWithoutContentDto;
import jsonbeautify.dto.SlugsDto;
import jsonbeautify.model.Post;
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

import java.util.ArrayList;
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
  private List<PostWithoutContentDto> findAll(@RequestParam(required = false) String type) {
    List<Post> posts = new ArrayList<>();
    if (type == null || type.trim().equals("")) {
      posts = postService.findAll();
    } else if (PostType.PAGE.name().equalsIgnoreCase(type)
        || PostType.POST.name().equalsIgnoreCase(type)
        || PostType.FORMATTER.name().equalsIgnoreCase(type)) {
      posts = postService.getPostsByType(type);
    } else if (PostType.UNKNOWN.name().equalsIgnoreCase(type) || type.equalsIgnoreCase("misc")) {
      posts = postService.getPostsByTypeUnknown();
    }
    return posts.stream().map(this::transform).collect(Collectors.toList());
  }

  @PostMapping("")
  private int create(@RequestBody PostWithoutContentDto dto) {
    Post post = postService.saveOrUpdate(transform(dto));
    return post == null ? -1 : post.getId();
  }

  @GetMapping("/{id}")
  private PostWithoutContentDto findById(@PathVariable int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      return transform(optional.get());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @GetMapping("/{id}/content")
  private PostContentDto getContentById(@PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      Post existingPost = optional.get();
      return new PostContentDto(existingPost.getContent());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @PutMapping("/{id}")
  private PostWithoutContentDto updateById(@RequestBody PostWithoutContentDto dto, @PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      Post existingPost = optional.get();
      existingPost.setTitle(dto.getTitle());
      existingPost.setKeywords(dto.getKeywords());
      existingPost.setDescription(dto.getDescription());
      existingPost.setSlug(dto.getSlug());
      existingPost.setType(dto.getType());
      existingPost.setActive(dto.isActive());
      return transform(postService.saveOrUpdate(existingPost));
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @PutMapping("/{id}/content")
  private PostWithoutContentDto updateContentById(
      @RequestBody PostContentDto dto, @PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      Post existingPost = optional.get();
      existingPost.setContent(dto.getContent());
      return transform(postService.saveOrUpdate(existingPost));
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @DeleteMapping("/{id}")
  private void deleteById(@PathVariable("id") int id) {
    postService.deleteById(id);
  }

  @GetMapping("getPostBySlug")
  private PostWithoutContentDto getPostBySlug(@RequestParam String slug) {
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
      return new PostContentDto(optional.get().getContent());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with slug: " + slug);
    }
  }

  @GetMapping("/getSlugsByType")
  public SlugsDto getSlugsByType(@RequestParam(required = false) String type) {
    SlugsDto dto = new SlugsDto();
    dto.setSlugs(postService.getSlugsByType(type));
    return dto;
  }

  private PostWithoutContentDto transform(Post post) {
    PostWithoutContentDto dto = new PostWithoutContentDto();
    dto.setActive(post.isActive());
    dto.setId(post.getId());
    dto.setType(post.getType());
    dto.setDescription(post.getDescription());
    dto.setKeywords(post.getKeywords());
    dto.setSlug(post.getSlug());
    dto.setTitle(post.getTitle());
    return dto;
  }

  private Post transform(PostWithoutContentDto dto) {
    Post post = new Post();
    post.setActive(dto.isActive());
    post.setType(dto.getType() == null ? PostType.UNKNOWN.name() : dto.getType());
    post.setDescription(dto.getDescription());
    post.setKeywords(dto.getKeywords());
    post.setSlug(dto.getSlug());
    post.setTitle(dto.getTitle());
    return post;
  }
}
