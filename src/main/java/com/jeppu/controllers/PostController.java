package com.jeppu.controllers;

import com.jeppu.config.AppConstants;
import com.jeppu.payloads.PageResponse;
import com.jeppu.payloads.PostDTO;
import com.jeppu.services.FileService;
import com.jeppu.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.images}")
    private String path;

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Long userId, @PathVariable Long categoryId) {
        PostDTO savedPostDTO = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(savedPostDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<PageResponse> getAllPostsByUser(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "asc", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageResponse pageResponse = this.postService.getAllPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<PageResponse> getAllPostsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "asc", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageResponse pageResponse = this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PageResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PageResponse pageResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    //post an image
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<?> uploadPostImage(
            @RequestParam("image") MultipartFile imageMultipartFile,
            @PathVariable("postId") Long postId
    ) {
        String imageFileName = "";
        PostDTO postDTO = null;
        try {
            postDTO = this.postService.getPostById(postId);
            imageFileName = this.fileService.uploadImage(path, imageMultipartFile);
            postDTO.setImageName(imageFileName);
            this.postService.updatePost(postDTO, postId);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return new ResponseEntity<>(Map.of("Image was NOT uploaded", ioException.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    //serve images
    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) {
        InputStream resource = null;
        try {
            resource = this.fileService.getResource(path, imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    //search by containing title
    @GetMapping("/posts/title/{keyword}")
    public ResponseEntity<List<PostDTO>> getPostsContainingTitle(@PathVariable("keyword") String keyword) {
        List<PostDTO> postDTOS = this.postService.getPostsContainingTitle(keyword);
        return ResponseEntity.ok(postDTOS);
    }


}
