package com.jeppu.repositories;

import com.jeppu.entities.Category;
import com.jeppu.entities.Post;
import com.jeppu.entities.User;
import com.jeppu.payloads.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByUser (User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    List<Post> findByTitleContaining(String title);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchPostsContainingTitle(@Param("key") String keyword);
}
