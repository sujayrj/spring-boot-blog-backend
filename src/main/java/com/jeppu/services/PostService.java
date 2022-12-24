package com.jeppu.services;

import com.jeppu.payloads.PostDTO;

import java.util.List;

public interface PostService {
    //create
    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);
    PostDTO updatePost(PostDTO postDTO, Long postId);
    void deletePost(Long postId);
    PostDTO getPostById(Long postId);
    List<PostDTO> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    List<PostDTO> getAllPostsByUser(Long userId);
    List<PostDTO> getAllPostsByCategory(Long categoryId);
}
