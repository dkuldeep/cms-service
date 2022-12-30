package jsonbeautify.repository;

import jsonbeautify.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

  @Query("SELECT p from Post p WHERE p.slug = ?1")
  public Post getBySlug(String slug);

  @Query("select p from Post p where p.type = :type ORDER BY p.modified desc ")
  public List<Post> getPostsByType(String type);

  @Query("select p from Post p where p.type is null or p.type not in :knownTypes")
  public List<Post> getPostsByTypeUnknown(List<String> knownTypes);

  @Query("select p from Post p where p.topic = :topic and p.type = 'POST' order by p.created desc ")
  List<Post> getPostsByTopic(String topic);

  @Query("select p from Post p where p.tags like %:tag% and p.type = 'POST' order by p.created desc ")
  List<Post> getPostsByTag(String tag);
}
