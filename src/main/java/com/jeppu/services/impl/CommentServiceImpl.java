package com.jeppu.services.impl;

import com.jeppu.entities.Comment;
import com.jeppu.entities.Post;
import com.jeppu.exceptions.ResourceNotFoundException;
import com.jeppu.payloads.CommentDTO;
import com.jeppu.payloads.PostDTO;
import com.jeppu.repositories.CommentRepo;
import com.jeppu.repositories.PostRepo;
import com.jeppu.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(postId)));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        post.getComments().add(comment);
        Comment savedComment = this.commentRepo.save(comment);
        this.postRepo.save(post);
        CommentDTO savedCommentDTO = this.modelMapper.map(savedComment, CommentDTO.class);
        return savedCommentDTO;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", String.valueOf(commentId)));
        this.commentRepo.delete(comment);
    }
}
