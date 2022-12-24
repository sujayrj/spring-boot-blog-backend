package com.jeppu.controllers;

import com.jeppu.payloads.ApiResponse;
import com.jeppu.payloads.CategoryDTO;
import com.jeppu.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategoryDTO(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("categoryId") Long id){
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, id);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategoryDTO(@PathVariable("categoryId") Long id){
        categoryService.deleteCategory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category deleted succesfully");
        apiResponse.setSuccess(Boolean.TRUE);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    //get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId") Long id){
        CategoryDTO categoryDTO = this.categoryService.getCategory(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    //getall
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> allCategories = this.categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

}
