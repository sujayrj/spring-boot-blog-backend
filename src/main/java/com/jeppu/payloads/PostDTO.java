package com.jeppu.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}
