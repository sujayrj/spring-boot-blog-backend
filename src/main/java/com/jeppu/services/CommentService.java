package com.jeppu.services;

import com.jeppu.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, Long postId);
    void deleteComment(Long commentId);

}
