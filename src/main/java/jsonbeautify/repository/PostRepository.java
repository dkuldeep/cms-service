package jsonbeautify.repository;

import jsonbeautify.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

  @Query(value = "SELECT * FROM tbl_post WHERE slug = ?1", nativeQuery = true)
  public Post getBySlug(String slug);

  @Query(value = "SELECT slug FROM tbl_post WHERE type = ?1 and active = ?2", nativeQuery = true)
  public List<String> getSlugsByType(String type, boolean active);

  @Query(value = "SELECT * FROM tbl_post WHERE type = ?1", nativeQuery = true)
  public List<Post> getPostsByType(String type);

  @Query(value = "SELECT * FROM tbl_post WHERE type is null or type = 'UNKNOWN'", nativeQuery = true)
  public List<Post> getPostsByTypeUnknown();
}
