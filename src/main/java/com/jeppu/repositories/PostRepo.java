package com.jeppu.repositories;

import com.jeppu.entities.Category;
import com.jeppu.entities.Post;
import com.jeppu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUser (User user);
    List<Post> findByCategory(Category category);
}
