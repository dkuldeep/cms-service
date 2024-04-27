package com.cms.repository;

import com.cms.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p from Post p WHERE p.slug = :slug")
    public Post getBySlug(String slug);
}
