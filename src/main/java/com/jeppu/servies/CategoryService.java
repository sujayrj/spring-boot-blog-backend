package com.jeppu.servies;

import com.jeppu.payloads.CategoryDTO;
import java.util.List;


public interface CategoryService {

    //CREATE
    public CategoryDTO createCategory(CategoryDTO categoryDTO);

    //UPDATE
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);

    //GET
    public CategoryDTO getCategory(Long id);

    //GETALL
    public List<CategoryDTO> getAllCategories();

    //DELETE
    public void deleteCategory(Long id);
}
