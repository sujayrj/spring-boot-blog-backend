package com.jeppu.controllers;

import com.jeppu.entities.Comment;
import com.jeppu.payloads.ApiResponse;
import com.jeppu.payloads.CommentDTO;
import com.jeppu.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO,
            @PathVariable("postId") Long postId){
        CommentDTO savedCommentDTO = this.commentService.createComment(commentDTO, postId);
        return new ResponseEntity<>(savedCommentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(Long commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted succesfully!", Boolean.TRUE), HttpStatus.OK);
    }

}
