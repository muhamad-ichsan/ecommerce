package com.backend.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;

import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new DuplicateKeyException("Duplicate entry for category: " + category.getName());
        }
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found"));
    }

    public Iterable<Category> findAll() {
        Sort ascendingSort = Sort.by(Sort.Order.asc("id"));
        Iterable<Category> listCategory = categoryRepository.findAll(ascendingSort);
        List<Category> categoryList = new ArrayList<>();
        listCategory.forEach(categoryList::add);
        if (!categoryList.isEmpty()) {
            return categoryList;
        } else {
            throw new EntityNotFoundException("No categories found");
        }
    }

    public void deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception ex) {
            throw new EntityNotFoundException("Category with ID " + id + " not found");
        }
    }
}

