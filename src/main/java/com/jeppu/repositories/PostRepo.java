package com.jeppu.repositories;

import com.jeppu.entities.Category;
import com.jeppu.entities.Post;
import com.jeppu.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByUser (User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
}
