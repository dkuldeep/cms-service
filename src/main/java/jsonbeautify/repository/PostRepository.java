package jsonbeautify.repository;

import jsonbeautify.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

  @Query(value = "SELECT * FROM tbl_post WHERE slug = ?1", nativeQuery = true)
  public Post getBySlug(String slug);

  @Query(
      value = "SELECT * FROM tbl_post WHERE type = ?1 ORDER BY created desc ",
      nativeQuery = true)
  public List<Post> getPostsByType(String type);

  @Query(value = "SELECT * FROM tbl_post WHERE type is null or type not in ?1", nativeQuery = true)
  public List<Post> getPostsByTypeUnknown(List<String> knownTypes);

  @Query("select p from Post p where p.topic = :topic and p.type = 'POST' order by p.created desc ")
  List<Post> getPostsByTopic(String topic);

  @Query("select p from Post p where p.tags like %:tag% and p.type = 'POST' order by p.created desc ")
  List<Post> getPostsByTag(String tag);
}
