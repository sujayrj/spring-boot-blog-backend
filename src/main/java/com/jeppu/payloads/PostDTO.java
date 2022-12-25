package com.jeppu.payloads;

import com.jeppu.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PostDTO {
    private Long postId;
    private String title;
    private String content;
    private String imageName;
    private LocalDate addedDate;
    private UserDTO userDTO;
    private CategoryDTO categoryDTO;
    private List<CommentDTO> commentDTOS = new ArrayList<>();
}
