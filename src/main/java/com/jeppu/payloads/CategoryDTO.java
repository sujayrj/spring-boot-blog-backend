package com.jeppu.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Long id;

    @NotBlank
    @Size(min=5, max = 50, message = "Category Title cannot be blank")
    private String categoryTitle;

    @NotEmpty
    @Size(min=5, max = 50, message = "Category Title cannot be blank")
    private String categoryDescription;
}
