package com.backend.ecommerce.controller;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ecommerce.dto.CategoryDto;
import com.backend.ecommerce.model.Category;
import com.backend.ecommerce.service.CategoryService;
import com.backend.ecommerce.util.ResponseData;
import com.backend.ecommerce.util.ResponseHandler;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	private final ModelMapper modelMapper = new ModelMapper();

	@PostMapping
	public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryDto categoryData, Errors errors) {
		return ResponseHandler.handleRequest(() -> {
			Category category = modelMapper.map(categoryData, Category.class);
			return categoryService.save(category);
		}, errors);
	}

	@PutMapping
	public ResponseEntity<ResponseData<Category>> update(@Valid @RequestBody CategoryDto categoryData, Errors errors) {
		return ResponseHandler.handleRequest(() -> {
			Optional<Category> opExistingCategory = Optional.ofNullable(categoryService.findById(categoryData.getId()));
			if (opExistingCategory.isPresent()) {
				Category existingCategory = opExistingCategory.get();
				existingCategory.setName(categoryData.getName());
				return categoryService.save(existingCategory);
			} else {
				throw new EntityNotFoundException("Category not found with ID: " + categoryData.getId());
			}
		}, errors);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseData<String>> deleteById(@PathVariable("id") Long id) {
		return ResponseHandler.handleRequest(() -> {
			categoryService.deleteById(id);
			return "Category deleted successfully";
		});
	}

	@GetMapping
	public ResponseEntity<ResponseData<Iterable<Category>>> findAll() {
		return ResponseHandler.handleRequest(() -> categoryService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseData<Category>> findById(@PathVariable("id") Long id) {
		return ResponseHandler.handleRequest(() -> categoryService.findById(id));
	}

}