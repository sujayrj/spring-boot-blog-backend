package com.jeppu.servies;

import com.jeppu.exceptions.ResourceNotFoundException;
import com.jeppu.payloads.CategoryDTO;
import com.jeppu.entities.Category;
import com.jeppu.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        CategoryDTO savedCategoryDTO = this.modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", id));
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        Category savedCategory = categoryRepo.save(category);
        CategoryDTO updatedCategoryDTO = this.modelMapper.map(savedCategory, CategoryDTO.class);
        return updatedCategoryDTO;
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));
        CategoryDTO categoryDTO = this.modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> allCategories = this.categoryRepo.findAll();
        return allCategories.stream().map(category -> {
            CategoryDTO categoryDTO = this.modelMapper.map(category, CategoryDTO.class);
            return categoryDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));
        this.categoryRepo.delete(category);
    }
}

