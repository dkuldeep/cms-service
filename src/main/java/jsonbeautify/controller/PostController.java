package jsonbeautify.controller;

import jsonbeautify.dto.SlugsDto;
import jsonbeautify.model.Post;
import jsonbeautify.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000", "https://jsonbeautify.net"})
@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("")
  private List<Post> findAll() {
    return postService.findAll();
  }

  @PostMapping("")
  private Post update(@RequestBody Post post) {
    return postService.saveOrUpdate(post);
  }

  @GetMapping("/{id}")
  private Post findById(@PathVariable int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @PutMapping("/{id}")
  private Post updateById(@RequestBody Post post, @PathVariable("id") int id) {
    Optional<Post> optional = postService.findById(id);
    if (optional.isPresent()) {
      Post existingPost = optional.get();
      existingPost.setContent(post.getContent());
      existingPost.setTitle(post.getTitle());
      existingPost.setMetaKeywords(post.getMetaKeywords());
      existingPost.setMetaDescription(post.getMetaDescription());
      existingPost.setSlug(post.getSlug());
      existingPost.setType(post.getType());
      return postService.saveOrUpdate(existingPost);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id);
    }
  }

  @DeleteMapping("/{id}")
  private void deleteById(@PathVariable("id") int id) {
    postService.deleteById(id);
  }

  @GetMapping("getPostBySlug")
  private Post getPostBySlug(@RequestParam String slug) {
    return postService.getBySlug(slug);
  }

  @GetMapping("/getSlugsByType")
  public SlugsDto getSlugsByType(@RequestParam String type) {
    SlugsDto dto = new SlugsDto();
    dto.setSlugs(postService.getSlugsByType(type));
    return dto;
  }
}
