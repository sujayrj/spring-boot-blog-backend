package com.jeppu.payloads;

import com.jeppu.entities.Post;
import com.jeppu.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private PostDTO postDTO;
}
