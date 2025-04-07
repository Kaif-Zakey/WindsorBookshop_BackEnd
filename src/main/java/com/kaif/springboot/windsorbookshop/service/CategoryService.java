package com.kaif.springboot.windsorbookshop.service;


import com.kaif.springboot.windsorbookshop.dto.CategoryDTO;
import com.kaif.springboot.windsorbookshop.entitis.Category;
import com.kaif.springboot.windsorbookshop.repo.BookRepo;
import com.kaif.springboot.windsorbookshop.repo.CategoryRepo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepo bookRepo;

    public List<Category> getAllCategories() {
        return modelMapper.map(categoryRepo.findAll(),
                new TypeToken<List<Category>>() {}.getType());
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsById(categoryDTO.getId())){
            throw new RuntimeException("Category already exists");
        }
            Category category = modelMapper.map(categoryDTO, Category.class);
            categoryRepo.save(category);

    }

    public Category getCategoryById(int id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updateCategory(int id, Category categoryDto) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(categoryDto.getName());
        categoryRepo.save(category);

        return new Category(category.getId(), category.getName());
    }

    public void deleteCategory(int id) {
        if (!categoryRepo.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }
        int bookCount = bookRepo.countByCategoryId(id);
        if (bookCount > 0) {
            throw new RuntimeException("Cannot delete category. It is associated with " + bookCount + " books.");
        }

        categoryRepo.deleteById(id);
    }
}
