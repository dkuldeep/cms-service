package jsonbeautify.service;

import jsonbeautify.model.Post;
import jsonbeautify.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<Post> findAll() {
    List<Post> posts = new ArrayList<>();
    postRepository.findAll().forEach(posts::add);
    return posts;
  }

  public Optional<Post> findById(int id) {
    return postRepository.findById(id);
  }

  public Post saveOrUpdate(Post post) {
    return postRepository.save(post);
  }

  public void deleteById(int id) {
    postRepository.deleteById(id);
  }

  public Post getBySlug(String slug) {
    return postRepository.getBySlug(slug);
  }

  public List<String> getSlugsByType(String type) {
    return postRepository.getSlugsByType(type, true);
  }
}
