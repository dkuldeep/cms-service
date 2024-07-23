package com.cms.repository;

import com.cms.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p from Post p WHERE p.slug = :slug")
    Post getBySlug(String slug);
}
