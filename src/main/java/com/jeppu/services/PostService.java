package com.jeppu.services;

import com.jeppu.payloads.PageResponse;
import com.jeppu.payloads.PostDTO;

import java.util.List;

public interface PostService {
    //create
    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);
    PostDTO updatePost(PostDTO postDTO, Long postId);
    void deletePost(Long postId);
    PostDTO getPostById(Long postId);
    PageResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    PageResponse getAllPostsByUser(Long userId, Integer pageNumber, Integer pageSize);
    PageResponse getAllPostsByCategory(Long categoryId, Integer pageNumber, Integer pageSize);

    List<PostDTO> getPostsContainingTitle(String keyword);
}
