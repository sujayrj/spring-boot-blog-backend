package com.jeppu.controllers;

import com.jeppu.payloads.PostDTO;
import com.jeppu.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Long userId, @PathVariable Long categoryId) {
        PostDTO savedPostDTO = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(savedPostDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsByUser(@PathVariable("userId") Long userId) {
        List<PostDTO> allPostsByUser = this.postService.getAllPostsByUser(userId);
        return new ResponseEntity<>(allPostsByUser, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<PostDTO> allPostsByUser = this.postService.getAllPostsByCategory(categoryId);
        return new ResponseEntity<>(allPostsByUser, HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        List<PostDTO> allPostsDTO = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPostsDTO, HttpStatus.OK);
    }

    //get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getAllPost(@PathVariable("postId") Long postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(Map.of("Post : " + postId, "Deleted succesfully"), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<?> udpatePost(@RequestBody PostDTO postDTO, @PathVariable("postId") Long postId) {
        PostDTO postDTO1 = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(postDTO1, HttpStatus.CREATED);
    }

}
